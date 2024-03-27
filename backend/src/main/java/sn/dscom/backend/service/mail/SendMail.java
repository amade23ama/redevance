package sn.dscom.backend.service.mail;

import jakarta.mail.internet.MimeMessage;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;


/**
 * SendMail
 */
public class SendMail implements IMailService {
    private static final Logger log= LoggerFactory.getLogger(SendMail.class);
    /**
     * JavaMailSender
     */
    private final JavaMailSender javaMailSender;

    /**
     * SendMail
     * @param javaMailSender javaMailSender
     */
    @Builder
    public SendMail(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     *
     * @param emailDetails emailDetails
     */
    @Override
    public void envoiMail( EmailDetails emailDetails) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@dcsom.com");
        message.setTo(emailDetails.getRecipient());
        message.setSubject(emailDetails.getSubject());
        message.setText(emailDetails.getMsgBody());
        //Envoi du message
        javaMailSender.send(message);
    }

    @Override
    public void envoiMail(EmailDetails emailDetails, boolean statut) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom("no-reply@dcsom.com");
            helper.setTo(emailDetails.getRecipient());
            helper.setSubject(emailDetails.getSubject());
            helper.setText(emailDetails.getMsgBody(), true);
            javaMailSender.send(mimeMessage);
            log.info("email envoyer avec successfully.");

        } catch (Exception e) {
            throw new RuntimeException("error envoie email", e);
        }
    }
}
