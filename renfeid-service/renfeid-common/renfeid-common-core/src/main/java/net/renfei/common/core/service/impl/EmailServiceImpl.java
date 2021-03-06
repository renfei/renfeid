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

import net.renfei.common.core.config.SystemConfig;
import net.renfei.common.core.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author renfei
 */
@Service
public class EmailServiceImpl implements EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    @Value("${spring.mail.username}")
    private String from;
    @Value("${spring.mail.reply-to}")
    private String replyTo;
    private final SystemConfig systemConfig;
    private final JavaMailSender emailSender;

    public EmailServiceImpl(SystemConfig systemConfig,
                            JavaMailSender emailSender) {
        this.systemConfig = systemConfig;
        this.emailSender = emailSender;
    }

    /**
     * ??????????????????
     *
     * @param to       ?????????
     * @param name     ???????????????
     * @param subject  ????????????
     * @param contents ????????????
     * @return
     */
    @Override
    public boolean send(String to, String name, String subject, List<String> contents) {
        return send(to, name, subject, contents, null);
    }

    /**
     * ??????????????????
     *
     * @param to       ?????????
     * @param name     ???????????????
     * @param subject  ????????????
     * @param contents ????????????
     * @return
     */
    @Override
    public boolean send(String to, String name, String subject, String contents) {
        return send(to, name, subject, contents, null);
    }

    /**
     * ??????????????????
     *
     * @param to         ?????????
     * @param name       ???????????????
     * @param subject    ????????????
     * @param contents   ????????????
     * @param attachment ????????????
     * @return
     */
    @Override
    public boolean send(String to, String name, String subject, List<String> contents, Map<String, File> attachment) {
        String txt = "";
        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("templates/email.html");
            Resource resource = resources[0];
            //???????????????????????????jar??????????????????????????????????????????????????????????????????????????????jar?????????????????????
            InputStream stream = resource.getInputStream();
            StringBuilder buffer = new StringBuilder();
            byte[] bytes = new byte[1024];
            try {
                for (int n; (n = stream.read(bytes)) != -1; ) {
                    buffer.append(new String(bytes, 0, n));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            txt = buffer.toString();
            txt = txt.replace("${NAME}", name);
            txt = txt.replace("${CONTENT}", getContent(contents));
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM y HH:mm:ss 'GMT+8'");
            Date d = new Date();
            String datetime = sdf.format(d);
            txt = txt.replace("${DATETIME}", datetime);
            sdf = new SimpleDateFormat("yyyy");
            String year = sdf.format(d);
            txt = txt.replace("${YEAR}", year);
        } catch (Exception e) {
            txt = getContent(contents);
        }
        //////
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(systemConfig.getSiteName() + " <" + from + ">");
            helper.setReplyTo(name + " <" + replyTo + ">");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(txt, true);
            if (attachment != null) {
                for (String key : attachment.keySet()
                ) {
                    FileSystemResource file = new FileSystemResource(attachment.get(key));
                    helper.addInline(key, file);
                }
            }
            emailSender.send(message);
            return true;
        } catch (MessagingException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * ??????????????????
     *
     * @param to         ?????????
     * @param name       ???????????????
     * @param subject    ????????????
     * @param contents   ????????????
     * @param attachment ????????????
     * @return
     */
    @Override
    public boolean send(String to, String name, String subject, String contents, Map<String, File> attachment) {
        String txt = "";
        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("templates/email.html");
            Resource resource = resources[0];
            //???????????????????????????jar??????????????????????????????????????????????????????????????????????????????jar?????????????????????
            InputStream stream = resource.getInputStream();
            StringBuilder buffer = new StringBuilder();
            byte[] bytes = new byte[1024];
            try {
                for (int n; (n = stream.read(bytes)) != -1; ) {
                    buffer.append(new String(bytes, 0, n));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            txt = buffer.toString();
            txt = txt.replace("${NAME}", name);
            txt = txt.replace("${CONTENT}", contents);
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM y HH:mm:ss 'GMT+8'");
            Date d = new Date();
            String datetime = sdf.format(d);
            txt = txt.replace("${DATETIME}", datetime);
            sdf = new SimpleDateFormat("yyyy");
            String year = sdf.format(d);
            txt = txt.replace("${YEAR}", year);
        } catch (Exception e) {
            txt = contents;
        }
        //////
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(systemConfig.getSiteName() + " <" + from + ">");
            helper.setReplyTo(name + " <" + replyTo + ">");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(txt, true);
            if (attachment != null) {
                for (String key : attachment.keySet()
                ) {
                    FileSystemResource file = new FileSystemResource(attachment.get(key));
                    helper.addInline(key, file);
                }
            }
            emailSender.send(message);
            return true;
        } catch (MessagingException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    private String getContent(List<String> contents) {
        StringBuilder sb = new StringBuilder();
        for (String s : contents
        ) {
            sb.append("<p>").append(s).append("</p>");
        }
        return sb.toString();
    }
}
