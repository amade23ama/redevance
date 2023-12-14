package sn.dscom.backend.batchs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

public class JobCompletionListener extends JobExecutionListenerSupport {
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
            log.error("Step Failed: ");
            log.error("Job Execution Erreur");
            //throw new RuntimeException();
        }
    }
}