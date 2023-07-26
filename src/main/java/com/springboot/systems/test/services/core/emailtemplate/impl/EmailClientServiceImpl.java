package com.springboot.systems.test.services.core.emailtemplate.impl;

import com.springboot.systems.test.models.core.setup.ApplicationSetting;
import com.springboot.systems.test.services.core.setup.ApplicationSettingService;
import com.springboot.systems.test.services.core.emailtemplate.EmailClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;
import java.util.Properties;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class EmailClientServiceImpl implements EmailClientService {

    private final ApplicationSettingService applicationSettingService;

    private static String EMAIL = "collins@bcs-ea.com";// change with your sender email
    private static String PASSWORD = "yyhmjgdpjiocmryu";// change with your sender password
    private static String SENDER = "systems@bcs-ea.com";
    private static String PORT = "587";
    private static String HOST = "smtp.gmail.com";

    private static Session session;

    private static String username;
    private static String password;

    @Override
    public void initMailSettings() {
        ApplicationSetting appSetting = applicationSettingService.getActiveApplicationSetting();
        if (appSetting != null) {
            HOST = (appSetting.getSystemEmailHost() != null || !appSetting.getSystemEmailHost().isEmpty())
                    ? appSetting.getSystemEmailHost()
                    : HOST;
            PORT = (appSetting.getSystemEmailPort() != null || !appSetting.getSystemEmailPort().isEmpty())
                    ? appSetting.getSystemEmailPort()
                    : PORT;
            EMAIL = (appSetting.getSystemEmail() != null || !appSetting.getSystemEmail().isEmpty())
                    ? appSetting.getSystemEmail()
                    : EMAIL;
            PASSWORD = (appSetting.getSystemEmailPassword() != null || !appSetting.getSystemEmailPassword().isEmpty())
                    ? appSetting.getSystemEmailPassword()
                    : PASSWORD;
            SENDER = (appSetting.getSystemEmailSender() != null || !appSetting.getSystemEmailSender().isEmpty())
                    ? appSetting.getSystemEmailSender()
                    : SENDER;
        }
    }

    @Override
    public void initMailService() {
        initMailSettings();
        log.info("EmailClientService: Initialising mail session...");

        if (session == null) {
            log.info("EmailClientService: Initialising mail session...");

            Properties props = new Properties();
            props.put("mail.smtp.auth", true);
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", HOST);
            props.put("mail.smtp.port", PORT);
            props.put("mail.smtp.ssl.trust", "*");
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");

            session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL, PASSWORD);
                }
            });

            username = EMAIL;
            password = PASSWORD;
        }
    }

    @Override
    @Async
    public void sendMail(String toAddress, String subject, String body) throws Exception {
        initMailService();
        log.info("Sending email...");

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SENDER));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
        message.setSubject(subject);

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(body, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);

        Transport.send(message, username, password);
    }

    @Override
    @Async
    public void sendMailWithAttachment(String toAddress, String subject, String body, byte[] attachment, String fileName) throws Exception {
        initMailService();
        log.info("Sending email...");

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SENDER));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
        message.setSubject(subject);

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(body, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        /*-----Add attachment-----*/
        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.setFileName(fileName);
        attachmentPart.setContent(attachment, "application/vnd.ms-excel");
        multipart.addBodyPart(attachmentPart);

        message.setContent(multipart);

        Transport.send(message, username, password);
    }

    @Override
    @Async
    public void sendMailWithAttachments(String toAddress, String subject, String body, byte[] workbookAttachment,
                                        String workbookName, byte[] invoiceAttachment, String invoiceName) throws Exception {
        initMailService();
        log.info("Sending email...");

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SENDER));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
        message.setSubject(subject);

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(body, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        /*-----Add attachments-----*/
        /*-----Workbook-----*/
        MimeBodyPart attachmentPart1 = new MimeBodyPart();
        attachmentPart1.setFileName(workbookName);
        attachmentPart1.setContent(workbookAttachment, "application/vnd.ms-excel");
        multipart.addBodyPart(attachmentPart1);

        /*-----Invoice-----*/
        MimeBodyPart attachmentPart2 = new MimeBodyPart();
        attachmentPart2.setFileName(invoiceName);
        attachmentPart2.setContent(invoiceAttachment, "application/pdf");
        multipart.addBodyPart(attachmentPart2);

        message.setContent(multipart);

        Transport.send(message, username, password);
    }

    @Override
    @Async
    public void sendMails(List<String> toAddresses, String subject, String body) throws Exception {
        initMailService();
        log.info("Sending emails...");

        for (String toAddress : toAddresses) {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
            message.setSubject(subject);

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport.send(message, username, password);

        }

    }

    @Override
    @Async
    public void sendMailsWithAttachment(List<String> toAddresses, String subject, String body, byte[] attachment, String fileName) throws Exception {
        initMailService();
        log.info("Sending emails...");

        for (String toAddress : toAddresses) {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
            message.setSubject(subject);

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            /*-----Add attachment-----*/
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.setFileName(fileName);
            attachmentPart.setContent(attachment, "application/vnd.ms-excel");
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            Transport.send(message, username, password);

        }

    }

}
