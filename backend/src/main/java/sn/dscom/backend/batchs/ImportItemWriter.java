package sn.dscom.backend.batchs;

import org.hibernate.tool.schema.TargetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import sn.dscom.backend.common.constants.Enum.StatutEnum;
import sn.dscom.backend.common.dto.*;
import sn.dscom.backend.database.entite.DepotEntity;
import sn.dscom.backend.service.ReferenceAnneeService;
import sn.dscom.backend.service.interfaces.IChargementService;
import sn.dscom.backend.service.interfaces.IDepotService;
import sn.dscom.backend.service.interfaces.ITransporteurService;
import sn.dscom.backend.service.interfaces.IVoitureService;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ImportItemWriter implements ItemWriter<List<ChargementDTO>> {
    private DepotDTO depot;
    @Autowired
    private IChargementService chargementService;
    @Autowired
    private IVoitureService voitureService;
    @Autowired
    private ITransporteurService transporteurService;
    @Autowired
    private IDepotService depotService;
    private StepExecution stepExecution;
    private  int lNbReDeposes=1;
    @Autowired
    ReferenceAnneeService referenceAnneeService;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }
    ImportItemWriter(DepotDTO depot){
        if(depot!=null) {
            this.depot = depot;
        }
    }
    private static final Logger log= LoggerFactory.getLogger(ImportItemWriter.class);

    @Override
    public void write(Chunk<? extends List<ChargementDTO>> chunk) throws Exception {
        List<Long> listannes=referenceAnneeService.getAllAnnee().stream().map(rech -> rech.getAnnee()).collect(Collectors.toList());
        Set<Long> newListAnnee= new HashSet<>();
        int i=1;
        for (List<ChargementDTO> chargementDTOList : chunk.getItems()) {

            AtomicInteger indexCounter = new AtomicInteger(0);
            for (ChargementDTO chargementDTO : chargementDTOList) {
                newListAnnee.add(this.getYear(chargementDTO.getDatePesage()));
                log.info("Enregistrement ChargementDTO: {}", indexCounter.getAndIncrement());
                if(chargementDTO.getTransporteur().getId()==null){
                    TransporteurDTO transporteurDTO=this.transporteurService.recherchercheTransporteurByNom(chargementDTO.getTransporteur().getNom());
                    if(transporteurDTO==null){
                        transporteurDTO= this.transporteurService.saveTransporteur(chargementDTO.getTransporteur());

                    }
                    chargementDTO.getTransporteur().setId(transporteurDTO.getId());
                }
                if(chargementDTO.getVehicule().getId()==null){
                    VehiculeDTO vehiculeDTO= this.voitureService.rechercherVehiculeByMatriculeAndIdTransporteurAndIdCategorie(
                            chargementDTO.getVehicule().getImmatriculation(),
                            chargementDTO.getVehicule().getCategorie().getId());
                    if(vehiculeDTO==null){
                        vehiculeDTO= this.voitureService.saveVehicule(chargementDTO.getVehicule());
                    }
                    chargementDTO.getVehicule().setId(vehiculeDTO.getId());
                }
                if(chargementDTO.getId()!=null){
                    this.stepExecution.getJobExecution().getExecutionContext().putInt("lNbChargementReDeposes",this.lNbReDeposes++);
                }
                depot.setSite(chargementDTO.getSite());
                depot.setNbChargementDeposes(i);
                chargementDTO.getDepotDTOList().add(depot);
                this.chargementService.miseAjourChargement(chargementDTO);
                i=i+1;
            }

        }
        newListAnnee.removeAll(listannes);
        if(!newListAnnee.isEmpty()){
            List<ReferenceAnneeDTO> referenceAnneeDTOS = newListAnnee.stream()
                    .map(value -> ReferenceAnneeDTO.builder().annee(value).build())
                    .collect(Collectors.toList());
           this.referenceAnneeService.saveAll(referenceAnneeDTOS);
        }
        int totalChargement = stepExecution.getJobExecution().getExecutionContext().getInt("totalChargement", 0);
        int lNbChargementReDeposes =  stepExecution.getJobExecution().getExecutionContext().getInt("lNbChargementReDeposes",0);
        int lNbChargementDeposes =   stepExecution.getJobExecution().getExecutionContext().getInt("lNbChargementDeposes",0);
        int lNbChargementDoublons =  stepExecution.getJobExecution().getExecutionContext().getInt("lNbChargementDoublons",0);
        int lNbChargementDeposesSucces =   stepExecution.getJobExecution().getExecutionContext().getInt("lNbChargementDeposesSucces",0);
        int lNbChargementError = stepExecution.getJobExecution().getExecutionContext().getInt("lNbChargementError",0);
        Optional<DepotDTO>  depotDTO=depotService.rechercherDepotById(depot.getId());
        if(depotDTO.isPresent()){
            DepotDTO depotFinal=depotDTO.get();
            depotFinal.setNbChargementDeposes(i-1);
            depotFinal.setNbChargementReDeposes(lNbChargementReDeposes);
            depotFinal.setNbChargementErreur(lNbChargementError);
            depotFinal.setDateHeureFinDepot( new Date());
            depotFinal.setSite(depot.getSite());
            depotFinal.setStatut(StatutEnum.SUCCES.getCode());
            depotService.enregistrerDepot(depotFinal);
        }
        log.info("numImport                  :{}",depot.getId());
        log.info("Site de Depot              :{}",depot.getSite().getNom());
        log.info("total Chargement           :{}",totalChargement);
        log.info("nombre Chargement Deposes  :{}",lNbChargementDeposes);
        log.info("nombre Chargement ReDeposes:{}",lNbChargementReDeposes);
        log.info("nombre Chargement Doublons :{}",lNbChargementDoublons);
        log.info("nombre Chargement Error    :{}",lNbChargementError);
    }
    private static Long getYear(Date date) {
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        return Long.parseLong(yearFormat.format(date));
    }
}
