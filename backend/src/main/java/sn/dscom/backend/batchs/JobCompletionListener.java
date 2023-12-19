package sn.dscom.backend.batchs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import sn.dscom.backend.common.constants.Enum.StatutEnum;
import sn.dscom.backend.common.dto.DepotDTO;
import sn.dscom.backend.service.interfaces.IDepotService;
import sn.dscom.backend.service.mail.EmailDetails;
import sn.dscom.backend.service.mail.IMailService;

import java.util.Date;
import java.util.Optional;

public class JobCompletionListener extends JobExecutionListenerSupport {
    @Autowired
    private IDepotService depotService;
    private static final Logger log= LoggerFactory.getLogger(JobCompletionListener.class);
    @Autowired
    private IMailService mailService;
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("démarrage du Job de traitement Chargement");
        if(BatchConfiguration.getDepotDTO()!=null){
            EmailDetails emailDetails=getmailDebutTraitement(BatchConfiguration.getDepotDTO());
            this.mailService.envoiMail(emailDetails,true);
        }

    }
    @Override
    public void afterJob(JobExecution jobExecution) {

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Fin de Traiement du Job completed successfully");
            if(BatchConfiguration.getDepotDTO()!=null){
                EmailDetails emailDetails=getmailFinTraitement(BatchConfiguration.getDepotDTO());
                this.mailService.envoiMail(emailDetails,true);
            }

        }else{
            if(jobExecution.getStatus() == BatchStatus.STOPPED ||jobExecution.getStatus() == BatchStatus.FAILED){
                Optional<DepotDTO> depotDTO=depotService.rechercherDepotById(BatchConfiguration.getDepotDTO().getId());
                if(depotDTO.isPresent()){
                    DepotDTO depotFinal=depotDTO.get();
                    depotFinal.setDateHeureFinDepot(new Date());
                    depotFinal.setStatut(StatutEnum.ERREUR.getCode());
                    depotService.enregistrerDepot(depotFinal);
                }
            }
            log.error("Fin de Traiement du Job avec erreur");
            if(BatchConfiguration.getDepotDTO()!=null) {
                EmailDetails emailDetails = getErrorTraitement(BatchConfiguration.getDepotDTO());
                this.mailService.envoiMail(emailDetails,true);
            }
        }

    }
    private  EmailDetails getmailDebutTraitement( DepotDTO depot) {
        String messageBody = "<html>" +
                "<body>" +
                "<p>Bonjour " +depot.getDeposeur().getPrenom()+", </p>" +
                "<p>Votre importation de données numéro " +depot.getId()+" a été lancée avec succès.</p>" +
                "<br>"+
                "<p>Cordialement</p>" +
                "</body>" +
                "</html>";
        return EmailDetails.builder()
                .subject("Début d'importation")
                .msgBody(messageBody)
                .recipient(depot.getDeposeur().getEmail())
                .build();

    }
    private  EmailDetails getErrorTraitement( DepotDTO depot) {
        String messageBody = "<html>" +
                "<body>" +
                "<p>Bonjour " +depot.getDeposeur().getPrenom()+", </p>" +
                "<p>Votre importation de données numéro " +depot.getId()+" s'est terminée en erreur.</p>" +
                "<br>"+
                "<p>Cordialement</p>" +
                "</body>" +
                "</html>";
        return EmailDetails.builder()
                .subject("Echec d'importation")
                .msgBody(messageBody)
                .recipient(depot.getDeposeur().getEmail())
                .build();



    }
    private  EmailDetails getmailFinTraitement( DepotDTO depot) {
        String messageBody = "<html>" +
                "<body>" +
                "<p>Bonjour " +depot.getDeposeur().getPrenom()+",</p>" +
                "<p>Votre importation de données numéro " +depot.getId()+" s'est terminée avec succès.</p>" +
                "<br>"+
                "<p>Cordialement</p>" +
                "</body>" +
                "</html>";
        return EmailDetails.builder()
                .subject("Importation succès")
                .msgBody(messageBody)
                .recipient(depot.getDeposeur().getEmail())
                .build();

    }
}