package sn.dscom.backend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sn.dscom.backend.batchs.BatchConfiguration;
import sn.dscom.backend.common.constants.Enum.StatutEnum;
import sn.dscom.backend.common.dto.DepotDTO;
import sn.dscom.backend.common.dto.UtilisateurDTO;
import sn.dscom.backend.service.ConnectedUtilisateurService;
import sn.dscom.backend.service.converter.UtilisateurConverter;
import sn.dscom.backend.service.interfaces.*;
import sn.dscom.backend.service.mail.IMailService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/depot/Batch")
public class BatchController {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job myJob;
    /** Logger Factory */
    private static final Logger log= LoggerFactory.getLogger(BatchController.class);

    /**
     * connected Utilisateur Service
     */

    /**
     * depot Service
     */
    @Autowired
    private IDepotService depotService;


    /**
     * environment
     */
    @Autowired
    private Environment environment;

    /**
     * connected Utilisateur Service
     */
    @Autowired
    private ConnectedUtilisateurService connectedUtilisateurService;


    /** mailService */
    @Autowired
    private IMailService mailService;

    @Autowired
    BatchConfiguration batchConfiguration;

    private final TaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
    @PostMapping("/upload")
    public ResponseEntity<Long> runBatchJob(@RequestParam("file") MultipartFile file,
                                              @RequestParam("mapEntete") String mapEnteteJson,
                                              @RequestParam("nom") String nom) {
        long timer1 = System.currentTimeMillis();
        DepotDTO depot = new DepotDTO();
        try {
            UtilisateurDTO utilisateurDTO = this.getConnectedUtilisateur().get();
            log.info("depot effectuer par {}",utilisateurDTO.getLogin());
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> mapInverse = new HashMap<>();
            nom = objectMapper.readValue(nom, new TypeReference<String>() {});
            depot = this.depotService.enregistrerDepot(buildDepot(file, utilisateurDTO, nom, StatutEnum.ENCOURS.getCode())).get();
            Map<String, String> mapDatabaseEnteteFile = objectMapper.readValue(mapEnteteJson, new TypeReference<Map<String, String>>() {});
            mapDatabaseEnteteFile.forEach((k, v) -> {mapInverse.put(v,k);});
            batchConfiguration.setBatchFile(file);
            batchConfiguration.processMapInverse(mapInverse);
            batchConfiguration.processEnvironment(this.environment);
            batchConfiguration.processDepot(depot);
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
            jobParametersBuilder.addLong("time", System.currentTimeMillis());
            JobExecution jobExecution = jobLauncher.run(myJob, jobParametersBuilder.toJobParameters());
            log.info("fin de lancement");
        }catch (Exception e){
            log.info("finnnnxxxxxxxxxxxxxx"+e.getMessage());
            e.printStackTrace();
        }
        log.info("----fin-----");
        long timer2 = System.currentTimeMillis();
        log.info("----duree----: " +(timer2-timer1) +"ms");
        return   ResponseEntity.ok(depot.getId());
    }
    @Async
    protected CompletableFuture<UtilisateurDTO> getConnectedUtilisateur(){
        return CompletableFuture.completedFuture(UtilisateurConverter.toUtilisateurDTO(connectedUtilisateurService.getConnectedUtilisateur()));
    }



    public JobExecution runJob(Job job, JobParameters jobParameters) throws ExecutionException, InterruptedException {
        CompletableFuture<JobExecution> completableFuture = new CompletableFuture<>();

        this.asyncTaskExecutor.execute(() -> {
            try {
                JobExecution jobExecution = jobLauncher.run(job, jobParameters);
                completableFuture.complete(jobExecution);
            } catch (JobExecutionException e) {
                completableFuture.completeExceptionally(e);
            }
        });

        // Attendez la fin de la tâche et récupérez le résultat
        return completableFuture.get();
    }
    private static DepotDTO buildDepot(MultipartFile file, UtilisateurDTO utilisateurDTO, String nom, String statut) {
        return DepotDTO.builder()
                .nom(nom)
                .statut(statut)
                .nbChargementReDeposes(0)
                .nbChargementDeposes(0)
                .nomFichier(file.getOriginalFilename())
                .nbChargementErreur(0)
                .dateHeureDepot(new Date())
                .deposeur(utilisateurDTO)
                .build();
    }
}
