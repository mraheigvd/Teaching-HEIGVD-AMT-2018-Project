package ch.heigvd.amt.wp1.util;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Tutorial followed : https://medium.com/@swhp/sending-email-with-payara-and-gmail-56b0b5d56882
 */
@Stateless
public class EmailSender {
    @Resource(name = "java/mail/swhp")
    Session mailSession;

    /**
     * Send a mail
     *
     * @param rcptTo the recipient
     * @param subject the mail subject
     * @param body the mail body
     * @throws MessagingException
    */
    public void sendEmail(String rcptTo, String subject, String body) throws MessagingException {
        Message message = new MimeMessage(mailSession);

        message.setSubject(subject);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(rcptTo));
        message.setContent(body, "text/html");
        Transport.send(message);
    }

    public static String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme() + "://";
        String serverName = request.getServerName();
        String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
        String contextPath = request.getContextPath();
        return scheme + serverName + serverPort + contextPath;
    }
}
