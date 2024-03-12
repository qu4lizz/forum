package qu4lizz.sni.forum.server.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender emailSender;
    private final Environment environment;

    public EmailService(JavaMailSender emailSender, Environment environment) {
        this.emailSender = emailSender;
        this.environment = environment;
    }

    @Async
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @Async
    public void sendHtmlMessage(String to, String subject, String htmlBody) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendLoginCode(String to, String code) {
        String subject = environment.getProperty("mail.message.login-code-title");
        String text = environment.getProperty("mail.message.login-code-message");
        assert text != null;
        String message = text.replace("<code>", code);

        sendHtmlMessage(to, subject, message);
    }

    @Async
    public void sendAccountApproved(String to) {
        String subject = environment.getProperty("mail.message.status-title");
        String text = environment.getProperty("mail.message.approved-message");

        sendHtmlMessage(to, subject, text);
    }

    @Async
    public void sendAccountRejected(String to) {
        String subject = environment.getProperty("mail.message.status-title");
        String text = environment.getProperty("mail.message.rejected-message");

        sendHtmlMessage(to, subject, text);
    }
}
