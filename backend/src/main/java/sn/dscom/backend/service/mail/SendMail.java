package sn.dscom.backend.service.mail;

import lombok.Builder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * SendMail
 */
public class SendMail implements IMailService {

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
}
