package com.example.exp.service.ServiceImpl;

import com.example.exp.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender javaMailSender;
    // not sure, check later
    @Value("${spring.mail.from}")
    private String from;
    @Override
    public void sendSimpleMail(String to, String subject, String content){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
    }
    @Override
    public void sendSimpleMail(String to, String subject, String content, String... cc){}

    /**
     * 发送HTML邮件
     * @param to
     * @param subject
     * @param content
     * @throws MessagingException
     */
    @Override
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException{}
    @Override
    public void sendHtmlMail(String to, String subject, String content, String... cc){}

    /**
     * 发送带附件的邮件
     * @param to
     * @param subject
     * @param content
     * @param filePath
     * @throws MessagingException
     */
    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) throws MessagingException{}
    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath, String... cc){}

    /**
     * 发送正文中有静态资源的邮件
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     * @throws MessagingException
     */
    @Override
    public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId) throws MessagingException{}
    @Override
    public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId, String... cc){}
}
