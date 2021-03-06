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
package net.renfei.common.core.service.impl;

import net.renfei.uaa.api.entity.UserDetail;
import net.renfei.common.api.exception.BusinessException;
import net.renfei.common.api.utils.StringUtils;
import net.renfei.common.core.service.EmailService;
import net.renfei.common.core.service.RedisService;
import net.renfei.common.core.service.SMSService;
import net.renfei.common.core.service.VerificationCodeService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static net.renfei.common.core.config.RedisConfig.REDIS_KEY_DATABASE;

/**
 * @author renfei
 */
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {
    private static final String REDIS_KEY_VERIFICATION_CODE = REDIS_KEY_DATABASE + ":VerificationCode:";
    private final SMSService smsService;
    private final RedisService redisService;
    private final EmailService emailService;

    public VerificationCodeServiceImpl(SMSService smsService,
                                       RedisService redisService,
                                       EmailService emailService) {
        this.smsService = smsService;
        this.redisService = redisService;
        this.emailService = emailService;
    }

    @Override
    public void sendVerificationCode(boolean numberVerificationCode, Date expires, String addressee,
                                     String authType, UserDetail account, String callBack) {
        String verificationCodeString;
        if (numberVerificationCode) {
            verificationCodeString = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        } else {
            verificationCodeString = StringUtils.getRandomNumber(6);
        }
        boolean isEmail = false, isPhone = false;
        if (StringUtils.isEmail(addressee)) {
            isEmail = true;
        } else if (StringUtils.isChinaPhone(addressee)) {
            isPhone = true;
        } else {
            throw new BusinessException("??????????????????????????????????????????");
        }
        // ???redis
        String key = REDIS_KEY_VERIFICATION_CODE + authType + ":" + addressee;
        redisService.set(key, verificationCodeString, (expires.getTime() - System.currentTimeMillis()) / 1000);
        if (isEmail) {
            List<String> contents = new ArrayList<>();
            String userName = "??????/??????";
            if (account != null) {
                userName = account.getUsername();
            }
            contents.add("????????? " + userName + " :");
            contents.add("???????????????[RENFEI.NET]????????????????????????");
            contents.add("????????????????????????????????????[RENFEI.NET]?????????????????????????????? Email ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????[RENFEI.NET]????????????????????????????????????????????????????????????????????????????????????????????????????????????");
            contents.add("----------------------------------------------------------------------");
            contents.add("??????????????????");
            contents.add("----------------------------------------------------------------------");
            contents.add("????????????[RENFEI.NET]??????????????????????????????????????? Email ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
            if (ObjectUtils.isEmpty(callBack)) {
                contents.add("?????????????????????");
                contents.add("<span style=\"color:red;font-size:18px;font-weight:800;\">" + verificationCodeString + "</span>");
            } else {
                contents.add("???????????????????????????????????????");
                contents.add("<a href=\"" + callBack + "?code=" + verificationCodeString + "\">" + callBack + "?code=" + verificationCodeString + "</a>");
                contents.add("(??????????????????????????????????????????????????????????????????????????????????????????)");
            }
            contents.add("----");
            contents.add("??????????????????????????????????????????");
            emailService.send(addressee, userName, "???????????????????????????[RENFEI.NET]?????????", contents);
        } else if (isPhone) {
            smsService.send(addressee, verificationCodeString);
        } else {
            throw new BusinessException("??????????????????????????????????????????");
        }
    }

    @Override
    public boolean verificationCode(String code, String addressee, String authType) {
        String key = REDIS_KEY_VERIFICATION_CODE + authType + ":" + addressee;
        if (redisService.hasKey(key)) {
            Object object = redisService.get(key);
            return object.toString().equals(code);
        } else {
            return false;
        }
    }
}
