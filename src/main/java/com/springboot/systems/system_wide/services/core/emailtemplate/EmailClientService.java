package com.springboot.systems.system_wide.services.core.emailtemplate;

import java.util.List;

public interface EmailClientService {

    /**
     * This method will be used for setting up the mail settings values.
     */
    void initMailSettings();

    /**
     * This method will be used for setting up the mail smtp properties.
     */
    void initMailService();

    /**
     * This method will be used for sending mails to single recipient.
     * @param toAddress, recipient
     * @param subject, subject of the mail
     * @param body, body of the mail
     */
    void sendMail(String toAddress, String subject, String body) throws Exception;

    /**
     * This method will be used for sending mails to single recipient with attachment.
     * @param toAddress, recipient
     * @param subject, subject of the mail
     * @param body, body of the mail
     * @param attachment, attachment
     * @param fileName, file name
     * @throws Exception, exception
     */
    void sendMailWithAttachment(String toAddress, String subject, String body, byte[] attachment, String fileName) throws Exception;

    /**
     * This method will be used for sending mails to single recipient with attachments.
     *
     * @param toAddress,         recipient
     * @param subject,           subject of the mail
     * @param body,              body of the mail
     * @param workbookAttachment, workbook attachment
     * @param workbookName,     workbook name
     * @param invoiceAttachment, invoice attachment
     * @param invoiceName,    invoice name
     * @throws Exception, exception
     */
    void sendMailWithAttachments(String toAddress, String subject, String body, byte[] workbookAttachment,
                                 String workbookName, byte[] invoiceAttachment, String invoiceName) throws Exception;

    /**
     * This method will be used for sending mails to multiple recipients.
     * @param toAddresses, list of recipients
     * @param subject, subject of the mail
     * @param body, body of the mail
     */
    void sendMails(List<String> toAddresses, String subject, String body) throws Exception;

    /**
     * This method will be used for sending mails to multiple recipients with attachment.
     * @param toAddresses, list of recipients
     * @param subject, subject of the mail
     * @param body, body of the mail
     * @param attachment, attachment
     * @param fileName, file name
     * @throws Exception, exception
     */
    void sendMailsWithAttachment(List<String> toAddresses, String subject, String body, byte[] attachment, String fileName) throws Exception;


}
