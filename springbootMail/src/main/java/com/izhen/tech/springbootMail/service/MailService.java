package com.izhen.tech.springbootMail.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


/**
 * @author zhen
 */
@Service
public class MailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMail(String to, String subject, String content){


        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        // 发件人
        message.setFrom(from);

        mailSender.send(message);
    }

    public void sendHtmlMail(String to, String subject,String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message,true);

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);

    }

    public void sendAttachmentsMail(String to, String subject, String content,
                                    String filePath) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);

        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = file.getFilename();
        helper.addAttachment(fileName, file);
        mailSender.send(message);
    }

    public void sendInlineResourceMail(String to, String subject, String content,
                                       String[] rscPathlist, String[] rscID)  {
        logger.info("发送静态邮件开始：{},{},{},{}",to,subject,content,rscPathlist,rscID);
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);


            for(int i=0;i<rscPathlist.length;i++){
                FileSystemResource resource = new FileSystemResource(new File(rscPathlist[i]));
                helper.addInline(rscID[i], resource);
            }

            mailSender.send(message);
            logger.info("发送静态图片邮件成功：");

        } catch (MessagingException e) {
            logger.error("发送静态邮件失败：",e);
        }
    }
}
