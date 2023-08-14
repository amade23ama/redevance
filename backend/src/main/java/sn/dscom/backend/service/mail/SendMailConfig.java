package sn.dscom.backend.service.mail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Send Mail Config
 */
@Configuration
public class SendMailConfig {

    /**
     * SendMail
     * @param javaMailSender javaMailSender
     * @return SendMail
     */
    @Bean
    public SendMail SendMail(JavaMailSender javaMailSender) {
        return SendMail.builder().javaMailSender(javaMailSender).build();
    }
}
