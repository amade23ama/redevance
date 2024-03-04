package sn.dscom.backend.batchs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import sn.dscom.backend.common.constants.Enum.ErreurEnum;
import sn.dscom.backend.common.dto.*;
import sn.dscom.backend.common.exception.CommonMetierException;
import sn.dscom.backend.common.util.ChargementUtils;
import sn.dscom.backend.service.interfaces.*;

import java.util.*;

public class ImportItemProcessor implements ItemProcessor<List<DepotDcsomDTO> , List<ChargementDTO>> {
    private static final Logger log= LoggerFactory.getLogger(ImportItemProcessor.class);
    private int nbChargementRedeposes;
    @Autowired
    private IProduitService produitService;
    @Autowired
    private IDepotService depotService;
    @Autowired
    private ISiteService siteService;
    @Autowired
    private IExploitationService exploitationService;
    /** categorie Service */
    @Autowired
    private ICategorieService categorieService;
    /** categorie Service */
    @Autowired
    private IVoitureService voitureService;

    /** categorie Service */
    @Autowired
    private ITransporteurService transporteurService;
    @Autowired
    private IChargementService chargementService;

    private  int lNbChargementReDeposes=0;
    private  int lNbChargementDeposes=0;
    private int lNbChargementDoublons=0;
    private int lNbChargementDeposesSucces=0;
    private int lNbChargementError = 0;
    private int totalChargement=0;
    private int nbChargementTotal=0;
    private StepExecution stepExecution;
    private List<ErreurDepotDTO> listErreur = new ArrayList<>();
    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
        this.lNbChargementError=0;

    }

    @Override
    public List<ChargementDTO> process(List<DepotDcsomDTO>  depotDcsomDTOList) throws CommonMetierException {
        List<ErreurDepotDTO> listErreur = new ArrayList<>();
        List<ChargementDTO> processedList = new ArrayList<>();
        List<ChargementDTO> listchargementDTO = new ArrayList<>();

        this.totalChargement=depotDcsomDTOList.size();
        stepExecution.getJobExecution().getExecutionContext().putInt("totalChargement",this.totalChargement);
        int i =1;
        List<CommonMetierException> errors = new ArrayList<>();
        for (DepotDcsomDTO depotDcsomDTO : depotDcsomDTOList) {
            log.info("traiment de la ligne : "+i);
            try {
                ChargementDTO chargementDTO = processSingleChargement(depotDcsomDTO);
                if(chargementDTO!=null){
                    processedList.add(chargementDTO);
                }else {
                    this.lNbChargementError=lNbChargementError+1;
                }

            }
          /*  catch (CommonMetierException e) {
                log.error("Erreur lors du traitement de la ligne : " + i);
                this.lNbChargementError=lNbChargementError+1;
                this.stepExecution.getJobExecution().getExecutionContext().putInt("lNbChargementError",this.lNbChargementError);
                errors.add(e);
            }

           */
            catch (Exception e) {

                log.error("Erreur non métier lors du traitement de la ligne : " + i);
                this.lNbChargementError=lNbChargementError+1;
                this.stepExecution.getJobExecution().getExecutionContext().putInt("lNbChargementError",this.lNbChargementError);
            }
        i++;
        }
        Set<ChargementDTO> listChargementDTOUnique = new HashSet<>(processedList);
        this.lNbChargementDoublons=processedList.size()-(listChargementDTOUnique.size());
        this.lNbChargementDeposesSucces=listChargementDTOUnique.size();
        this.stepExecution.getJobExecution().getExecutionContext().putInt("lNbChargementDeposesSucces",this.lNbChargementDeposesSucces);
        this.stepExecution.getJobExecution().getExecutionContext().putInt("lNbChargementDoublons",this.lNbChargementDoublons);
        this.stepExecution.getJobExecution().getExecutionContext().putInt("lNbChargementError",this.lNbChargementError);
        return listChargementDTOUnique.stream().toList();
    }


    private ChargementDTO processSingleChargement(DepotDcsomDTO depotDcsomDTO) throws CommonMetierException {
        try {

        VehiculeDTO vehiculeDTO=null;
        ChargementDTO chargementDTO= null;
        ProduitDTO produitDTO=this.produitService.rechercherProduitByNom(depotDcsomDTO.getNomProduit());
        SiteDTO siteDTO= this.siteService.rechercherSiteByNom(depotDcsomDTO.getNomSite());
        ExploitationDTO exploitationDTO=this.exploitationService.rechercheSiteExploitationByNom(depotDcsomDTO.getExploitation());
        CategorieDTO categorieDTO=this.categorieService.rechercheCategorieByType(depotDcsomDTO.getType());
        TransporteurDTO transporteurDTO=this.transporteurService.recherchercheTransporteurByNom(depotDcsomDTO.getNomTransport());
        if((siteDTO!=null ||produitDTO!=null||exploitationDTO!=null||categorieDTO!=null) && depotDcsomDTO.getDatePesage()!=null &&  depotDcsomDTO.getHeurePesage()!=null ){
            if(transporteurDTO==null){
                transporteurDTO= TransporteurDTO.builder().nom(depotDcsomDTO.getNomTransport())
                        .type("S")
                        .build();

            }

            if(categorieDTO.getId()!=null){
                vehiculeDTO= this.voitureService.rechercherVehiculeByMatriculeAndIdTransporteurAndIdCategorie(
                        depotDcsomDTO.getMatricule(),categorieDTO.getId());
            }
            if (vehiculeDTO == null) {
                vehiculeDTO = VehiculeDTO.builder()
                        .immatriculation(depotDcsomDTO.getMatricule())
                        .categorie(categorieDTO)
                        .build();
            }
          if(produitDTO!=null && siteDTO!=null && exploitationDTO!=null&& vehiculeDTO!=null && depotDcsomDTO.getDestination()!=null
                  &&depotDcsomDTO.getPoidsMax()!=null &&depotDcsomDTO.getPoidsMax()!=null && transporteurDTO!=null){
              chargementDTO=chargementService.genereLineChargement(vehiculeDTO, siteDTO, exploitationDTO, produitDTO,
                      depotDcsomDTO.getDestination(), depotDcsomDTO.getPoidsMesure(),
                      depotDcsomDTO.getPoidsMax(), depotDcsomDTO.getDatePesage(),
                      depotDcsomDTO.getHeurePesage(),transporteurDTO);
              ChargementDTO chargementDTOTrouve= chargementService.recherChargementByEntity(chargementDTO);
              if(chargementDTOTrouve!=null){
                  this.lNbChargementReDeposes=this.lNbChargementReDeposes+1;
                  log.info("chargement existe id: "+chargementDTOTrouve.getId());
                  this.stepExecution.getJobExecution().getExecutionContext().putInt("lNbChargementReDeposes",this.lNbChargementReDeposes);
                  if(vehiculeDTO.getId()!=null && vehiculeDTO.getPoidsVide()!=null) {
                      chargementDTOTrouve.setPoidsSubst(chargementDTO.getPoidsSubst());
                      chargementDTOTrouve.setVolumeSubst(chargementDTO.getVolumeSubst());
                      chargementDTOTrouve.setVolumeMoyen(chargementDTO.getVolumeMoyen());
                      chargementDTOTrouve.setEcart(chargementDTO.getEcart());
                  }
                  chargementDTOTrouve.setDateModif(new Date());
                  chargementDTO=chargementDTOTrouve;
              }
          }
                this.lNbChargementDeposes++;
            this.stepExecution.getJobExecution().getExecutionContext().putInt("lNbChargementDeposes",this.lNbChargementDeposes);
        }

            return chargementDTO;

        }catch (CommonMetierException e){
            throw new CommonMetierException(HttpStatus.NOT_FOUND.value(), ErreurEnum.ERR_NOT_FOUND);
        }
    }
    public List<ErreurDepotDTO> getListErreur() {
        return listErreur;
    }
}