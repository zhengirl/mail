package com.izhen.tech.springbootMail.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServieTest {
    @Resource
    MailService mailService;
    @Resource
    TemplateEngine templateEngine;

    @Test
    public void sendSimpleMailTest(){
        mailService.sendSimpleMail("1358112181@qq.com", "这是第一封邮件",
                "大家好，我是VAE，这是我的第一张专辑，送给你！");
    }

    @Test
    public void sendHtmlMailTest() throws MessagingException {
        String content = "<html>\n"+
                "<body>\n" +
                "<h1> hello world,这是一封HTML邮件！</h1>\n"+
                "</body>\n"+
                "</html>";
        mailService.sendHtmlMail("1358112181@qq.com", "这是第一封邮件",content);
    }

    @Test
    public void sendAttachMailTest() throws MessagingException {
        String filePath = "/Volumes/Macu/zhen/jav/helloWorld/helloWorld.zip";
        mailService.sendAttachmentsMail("m201873193@hust.edu.cn","这是一封带附件的邮件",
                "大家好，记得查看附件哦",filePath);
    }

    @Test
    public void sendInlineResourceMailTest() {
        String[] imgPath = {"/Volumes/Macu/zhen/jav/helloWorld/3.jpg"};
        String[] rscID = {"zhen001"};
        String content = "<html><body>" +
                "远远真是一个可爱的男孩子啊😙😙😙" +"<br />"+
                ""+"<img src=\'cid:"+rscID[0]+
                "\'> </img>" +"<br />"+
                "</body></html>";
        mailService.sendInlineResourceMail("moilk@qq.com","这是一封有爱的邮件",
                content,imgPath,rscID);
    }

    @Test
    public void testTemplateMailTest() throws MessagingException {
        Context context = new Context();
        context.setVariable("id", "006");

        String emailContent=templateEngine.process("emailTemplate",context);

        mailService.sendHtmlMail("m201873193@hust.edu.cn","模板邮件",emailContent);

    }





}
