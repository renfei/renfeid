/*
 *   Copyright 2022 RenFei(i@renfei.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.renfei.uaa.service;

import net.renfei.common.api.constant.APIResult;
import net.renfei.common.api.constant.enums.StateCodeEnum;
import net.renfei.common.api.entity.UserInfo;
import net.renfei.common.api.exception.BusinessException;
import net.renfei.common.api.utils.ListUtils;
import net.renfei.common.api.utils.RSAUtils;
import net.renfei.common.api.utils.StringUtils;
import net.renfei.common.core.config.RedisConfig;
import net.renfei.common.core.config.SystemConfig;
import net.renfei.uaa.api.entity.UserDetail;
import net.renfei.common.core.service.RedisService;
import net.renfei.common.core.service.SystemService;
import net.renfei.common.core.service.VerificationCodeService;
import net.renfei.common.core.utils.AESUtils;
import net.renfei.common.core.utils.IpUtils;
import net.renfei.proprietary.discuz.service.DiscuzService;
import net.renfei.uaa.api.AuthorizationService;
import net.renfei.uaa.api.JwtService;
import net.renfei.uaa.api.UserService;
import net.renfei.uaa.api.entity.*;
import net.renfei.uaa.repositories.UaaSecretKeyMapper;
import net.renfei.uaa.repositories.entity.UaaSecretKeyExample;
import net.renfei.uaa.repositories.entity.UaaSecretKeyWithBLOBs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import static net.renfei.common.core.config.SystemConfig.MAX_USERNAME_LENGTH;

/**
 * ????????????
 *
 * @author renfei
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    public final static String REDIS_TOKEN_KEY = RedisConfig.REDIS_KEY_DATABASE + ":token:";
    private final static Logger logger = LoggerFactory.getLogger(AuthorizationServiceImpl.class);
    private final static Pattern SPECIAL_PATTERN = Pattern.compile("[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~???@#???%??????&*????????????+|{}????????????????????????????????????]|\n|\r|\t");
    private final JwtService jwtService;
    private final UserService userService;
    private final SystemConfig systemConfig;
    private final RedisService redisService;
    private final SystemService systemService;
    private final DiscuzService discuzService;
    private final UaaSecretKeyMapper uaaSecretKeyMapper;
    private final VerificationCodeService verificationCodeService;

    public AuthorizationServiceImpl(JwtService jwtService,
                                    UserService userService,
                                    SystemConfig systemConfig,
                                    RedisService redisService,
                                    SystemService systemService,
                                    DiscuzService discuzService,
                                    UaaSecretKeyMapper uaaSecretKeyMapper,
                                    VerificationCodeService verificationCodeService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.systemConfig = systemConfig;
        this.redisService = redisService;
        this.systemService = systemService;
        this.discuzService = discuzService;
        this.uaaSecretKeyMapper = uaaSecretKeyMapper;
        this.verificationCodeService = verificationCodeService;
    }

    /**
     * ?????????????????????????????????
     *
     * @return
     */
    @Override
    public APIResult<SecretKey> requestServerSecretKey() {
        Map<Integer, String> map = RSAUtils.genKeyPair(4096);
        if (ObjectUtils.isEmpty(map)) {
            logger.error("??????????????????????????????");
            throw new RuntimeException("??????????????????????????????");
        }
        String uuid = UUID.randomUUID().toString();
        SecretKey secretKey = new SecretKey();
        secretKey.setUuid(uuid);
        secretKey.setPublicKey(map.get(0));
        // ???????????????
        secretKey.setPrivateKey(null);
        // ???????????????
        UaaSecretKeyWithBLOBs uaaSecretKey = new UaaSecretKeyWithBLOBs();
        uaaSecretKey.setPublicKey(map.get(0));
        uaaSecretKey.setPrivateKey(map.get(1));
        uaaSecretKey.setUuid(uuid);
        uaaSecretKey.setCreateTime(new Date());
        uaaSecretKeyMapper.insertSelective(uaaSecretKey);
        return new APIResult<>(secretKey);
    }

    /**
     * ?????????????????????????????????AES??????
     *
     * @param secretKey ???????????????????????????
     * @return
     */
    @Override
    public APIResult<SecretKey> settingClientSecretKey(SecretKey secretKey) {
        if (secretKey == null || secretKey.getUuid() == null || secretKey.getUuid().isEmpty()) {
            throw new BusinessException("??????????????????UUID??????????????????????????????");
        }
        UaaSecretKeyExample example = new UaaSecretKeyExample();
        example.createCriteria().andUuidEqualTo(secretKey.getUuid());
        UaaSecretKeyWithBLOBs uaaSecretKey = ListUtils.getOne(uaaSecretKeyMapper.selectByExampleWithBLOBs(example));
        if (uaaSecretKey == null) {
            throw new BusinessException("??????????????????UUID??????????????????????????????");
        }
        String clientKey;
        try {
            clientKey = URLDecoder.decode(RSAUtils.decrypt(secretKey.getPublicKey(), uaaSecretKey.getPrivateKey()), "utf-8");
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new BusinessException("??????????????????????????????");
        }
        String aes = StringUtils.getRandomString(16);
        String aesEnc;
        try {
            aesEnc = RSAUtils.encrypt(aes, clientKey);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new RuntimeException("??????????????????????????????RSA??????????????????????????????");
        }
        //??????AES
        String uuid = UUID.randomUUID().toString();
        UaaSecretKeyWithBLOBs clientPrivateKey = new UaaSecretKeyWithBLOBs();
        clientPrivateKey.setUuid(uuid);
        clientPrivateKey.setCreateTime(new Date());
        clientPrivateKey.setPrivateKey(aes);
        uaaSecretKeyMapper.insertSelective(clientPrivateKey);
        SecretKey responseSecretKey = new SecretKey();
        responseSecretKey.setUuid(uuid);
        responseSecretKey.setPublicKey(aesEnc);
        responseSecretKey.setPrivateKey(aesEnc);
        return new APIResult<>(responseSecretKey);
    }

    /**
     * ????????????ID??????AES??????
     *
     * @param string ??????
     * @param keyId  ??????ID
     * @return ??????
     */
    @Override
    public APIResult<String> decryptAesByKeyId(String string, String keyId) {
        UaaSecretKeyExample example = new UaaSecretKeyExample();
        example.createCriteria().andUuidEqualTo(keyId);
        UaaSecretKeyWithBLOBs uaaSecretKey = ListUtils.getOne(uaaSecretKeyMapper.selectByExampleWithBLOBs(example));
        if (uaaSecretKey == null) {
            throw new BusinessException("AESKeyId?????????");
        }
        try {
            string = AESUtils.decrypt(string, uaaSecretKey.getPrivateKey());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new BusinessException("????????????????????????");
        }
        return new APIResult<>(string);
    }

    /**
     * ??????
     *
     * @param signIn ??????????????????
     * @return ????????????
     */
    @Override
    public APIResult<SignInVo> signIn(SignInAo signIn, HttpServletRequest request) {
        signIn.setUserName(signIn.getUserName().trim().toLowerCase());
        signIn.setPassword(this.decryptAesByKeyId(
                signIn.getPassword(), signIn.getKeyUuid()
        ).getData());
        UserDetail userDetail = userService.signIn(signIn, request).getData();
        String token = jwtService.createJWT(userDetail.getUsername(), IpUtils.getIpAddress(request)).getData();
        // ?????????????????????token???redis?????????
        redisService.del(REDIS_TOKEN_KEY + userDetail.getUsername());
        redisService.set(REDIS_TOKEN_KEY + userDetail.getUsername(), token);
        redisService.expire(REDIS_TOKEN_KEY + userDetail.getUsername(), systemConfig.getJwt().getExpiration() / 1000);
        SignInVo signInVo = new SignInVo();
        signInVo.setAccessToken(token);
        if (systemConfig.getUCenter().getEnable()) {
            signInVo.setUcScript(discuzService.uCenterSynLogin(userDetail.getUsername()));
        }
        return new APIResult<>(signInVo);
    }

    @Override
    public APIResult signUp(SignUpAo signUp, HttpServletRequest request) {
        if (systemConfig.getEnableSignUp()) {
            if (signUp.getUserName().trim().toLowerCase().length() >= MAX_USERNAME_LENGTH) {
                return APIResult.builder().code(StateCodeEnum.Failure).message("????????????????????????????????????????????????" + MAX_USERNAME_LENGTH).build();
            }
            if (signUp.getEmail().length() >= MAX_USERNAME_LENGTH) {
                return APIResult.builder().code(StateCodeEnum.Failure).message("????????????????????????????????????????????????" + MAX_USERNAME_LENGTH).build();
            }
            if (ObjectUtils.isEmpty(signUp.getUserName().trim())) {
                return APIResult.builder().code(StateCodeEnum.Failure).message("?????????????????????").build();
            }
            if (signUp.getUserName().trim().getBytes().length < 4) {
                return APIResult.builder().code(StateCodeEnum.Failure).message("????????????????????????????????????????????????").build();
            }
            if (ObjectUtils.isEmpty(signUp.getEmail().trim())) {
                return APIResult.builder().code(StateCodeEnum.Failure).message("????????????????????????").build();
            }
            if (StringUtils.isEmail(signUp.getUserName().trim())) {
                return APIResult.builder().code(StateCodeEnum.Failure).message("??????????????????????????????????????????????????????").build();
            }
            if (StringUtils.isChinaPhone(signUp.getUserName().trim())) {
                return APIResult.builder().code(StateCodeEnum.Failure).message("???????????????????????????????????????????????????????????????????????????????????????????????????").build();
            }
            if (StringUtils.isDomain(signUp.getUserName().trim())) {
                return APIResult.builder().code(StateCodeEnum.Failure).message("???????????????????????????????????????????????????").build();
            }
            if (SPECIAL_PATTERN.matcher(signUp.getUserName().trim()).find()) {
                return APIResult.builder().code(StateCodeEnum.Failure).message("??????????????????????????????????????????????????????").build();
            }
            if (ObjectUtils.isEmpty(signUp.getPassword())) {
                return APIResult.builder().code(StateCodeEnum.Failure).message("??????????????????").build();
            }
            if (!StringUtils.isEmail(signUp.getEmail().trim())) {
                return APIResult.builder().code(StateCodeEnum.Failure).message("?????????????????????????????????????????????").build();
            }
            signUp.setPassword(this.decryptAesByKeyId(
                    signUp.getPassword(), signUp.getKeyUuid()
            ).getData());
            APIResult apiResult = userService.signUp(signUp, request);
            if (apiResult.getCode() == 200 && systemConfig.getUCenter().getEnable()) {
                // ????????????Discuz??????
                discuzService.uCenterSynSignUp(signUp.getUserName(), signUp.getEmail(), request);
            }
            return apiResult;
        } else {
            return APIResult.builder().code(StateCodeEnum.Failure).message("???????????????????????????????????????????????????????????????????????????????????????").build();
        }
    }

    @Override
    public APIResult signOut(UserDetail userDetail, HttpServletRequest request) {
        if (userDetail != null) {
            redisService.del(REDIS_TOKEN_KEY + userDetail.getUsername());
        }
        return APIResult.success();
    }

    @Override
    public void activation(SignUpActivationAo signUpActivation) {
        if (!StringUtils.isEmail(signUpActivation.getEmailOrPhone())
                && !StringUtils.isChinaPhone(signUpActivation.getEmailOrPhone())) {
            // ????????????????????????????????????????????????????????????
            throw new BusinessException("????????????????????????????????????");
        }
        if (!verificationCodeService.verificationCode(signUpActivation.getCode(), signUpActivation.getEmailOrPhone(), "SIGN_UP")) {
            // ???????????????????????????
            throw new BusinessException("???????????????????????????");
        }
        userService.activation(signUpActivation.getEmailOrPhone());
    }

    @Override
    public UserInfo requestCurrentUserInfo() {
        UserDetail userDetail = systemService.currentUserDetail();
        if (userDetail != null) {
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(userDetail, userInfo);
            return userInfo;
        }
        return null;
    }
}
