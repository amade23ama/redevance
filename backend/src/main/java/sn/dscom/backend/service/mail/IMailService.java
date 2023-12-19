package sn.dscom.backend.service.mail;

/**
 * IMailService
 */
public interface IMailService {

    /**
     *
     * @param emailDetails emailDetails
     */
    void envoiMail(EmailDetails emailDetails);
    void envoiMail(EmailDetails emailDetails,boolean statut);
}
