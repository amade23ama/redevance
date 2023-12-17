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

import java.util.Date;
import java.util.Optional;

public class JobCompletionListener extends JobExecutionListenerSupport {
    @Autowired
    private IDepotService depotService;
    private  BatchConfiguration batchConfiguration;
    private static final Logger log= LoggerFactory.getLogger(JobCompletionListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("démarrage du Job de traitement Chargement");
    }
    @Override
    public void afterJob(JobExecution jobExecution) {

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Fin de Traiement du Job completed successfully");
        } else {
            log.error("Fin de Traiement du Job avec Erreur: ");
            if(BatchConfiguration.getDepotDTO()!=null){
                Optional<DepotDTO> depotDTO=depotService.rechercherDepotById(BatchConfiguration.getDepotDTO().getId());
                if(depotDTO.isPresent()){
                    DepotDTO depotFinal=depotDTO.get();
                    depotFinal.setStatut(StatutEnum.ERREUR.getCode());
                    depotService.enregistrerDepot(depotFinal);
                }
            }
        }

    }
}