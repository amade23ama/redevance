package sn.dscom.backend.common.util;

import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import sn.dscom.backend.common.dto.*;
import sn.dscom.backend.service.exeptions.DscomTechnicalException;
import sn.dscom.backend.service.interfaces.IChargementService;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class CustomRecursiveImport extends RecursiveAction {
    private static final int THRESHOLD = 10000;
    private final List<String[]> data;
    List<String> header;
    private final DepotDTO depotDTO;
    List<ProduitDTO> referentielProduits;
    List<ExploitationDTO> referentielSitesExploitation;
    List<CategorieDTO> referentielCategorie;
    List<SiteDTO> referentielSite;

    Map<String, String> mapInverse;

    /**
     * environment
     */
    private Environment environment;

    private static final Logger logger = Logger.getAnonymousLogger();

    /**
     * chargement Service
     */
   // @Autowired
    private IChargementService chargementService;

    public CustomRecursiveImport(List<String[]> data, List<String> header, DepotDTO depotDTO,
                                 List<ProduitDTO> referentielProduits, List<ExploitationDTO> referentielSitesExploitation,
                                 List<CategorieDTO> referentielCategorie, List<SiteDTO> referentielSite, Map<String, String> mapInverse,
                                 Environment environment, IChargementService chargementService) {
    this.data = data;
    this.header = header;
    this.depotDTO = depotDTO;
    this.referentielProduits = referentielProduits;
    this.referentielSitesExploitation = referentielSitesExploitation;
    this.referentielCategorie = referentielCategorie;
    this.referentielSite = referentielSite;
    this.mapInverse = mapInverse;
    this.environment = environment;
    this.chargementService = chargementService;
    }

    /**
     * The main computation performed by this task.
     */
    @Override
    protected void compute() {
        if (this.data.size() > CustomRecursiveImport.THRESHOLD){
            ForkJoinTask.invokeAll(createSubtasks());
        }else {
            try {
                this.processing(this.data);
            } catch (ExecutionException | InterruptedException | DscomTechnicalException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<CustomRecursiveImport> createSubtasks() {
        List<CustomRecursiveImport> subtasks = new ArrayList<>();

        List<String[]> partOne = this.data.subList(0, this.data.size() / 2);
        List<String[]> partTwo = this.data.subList(this.data.size() / 2, this.data.size());

        subtasks.add(new CustomRecursiveImport(partOne, this.header, this.depotDTO, this.referentielProduits, this.referentielSitesExploitation
        ,this.referentielCategorie,this.referentielSite, this.mapInverse, environment, chargementService));
        subtasks.add(new CustomRecursiveImport(partTwo, this.header, this.depotDTO, this.referentielProduits, this.referentielSitesExploitation
                ,this.referentielCategorie,this.referentielSite, this.mapInverse, environment, chargementService));

        return subtasks;
    }

    private void processing(List<String[]> listOfChargement) throws ExecutionException, InterruptedException, DscomTechnicalException {
        //String result = work.toUpperCase();
        logger.info("This result - was processed by "
                + Thread.currentThread().getName());

        for (String[] nextLine :  listOfChargement) {
            List<String> chargement = tabToList(nextLine);
            if (chargement.size() != 0) {
                CustomRecursiveImport.logger.info(String.format("Chargement de ligne : %s", chargement));
                // On recupère le produit dans le chagement: On fait un chargement que pour les produit qui existe
                Optional<ProduitDTO> produitDTO = this.getCurrentProduct(chargement, mapInverse, header, referentielProduits).get();
                // On recupère le siteExploitation dans le chagement: On fait un chargement que pour les sites qui existe
                Optional<ExploitationDTO> exploitationDTO = this.getCurrentExploitation(chargement, mapInverse, header, referentielSitesExploitation).get();
                // On recupère le siteExploitation dans le chagement: On fait un chargement que pour les sites qui existe
                Optional<CategorieDTO> categorieDTO = this.getCurrentCategorie(chargement, mapInverse, header, referentielCategorie).get();

                SiteDTO siteDTO = this.rechercherSite(chargement, mapInverse, header, referentielSite);

                if(produitDTO.isPresent() && exploitationDTO.isPresent()  && categorieDTO.isPresent() && siteDTO != null){
                    //Chargement d'une ligne du fichier
                    this.chargementService.effectuerChargement(chargement, mapInverse, header, this.depotDTO, produitDTO.get(), exploitationDTO.get(), categorieDTO.get(), siteDTO);
                }
            }
        }


    }

    /**
     * rechercher site en base
     *
     * @param ligneChargement siteDTO
     * @param mapCorrespondance maps
     * @return l'objet enregisté
     */
    private SiteDTO rechercherSite(List<String> ligneChargement, Map<String, String> mapCorrespondance, List<String> header, List<SiteDTO> referentielSite){
        String siteName = ligneChargement.get(header.indexOf(mapCorrespondance.get(this.environment.getProperty("db.site.nom")))).toUpperCase();
        Optional<SiteDTO> siteDTO = referentielSite.stream().filter(site -> siteName.trim().equals(site.getNom())).findFirst();
        if (siteDTO.isEmpty()) {
            CustomRecursiveImport.logger.info(String.format("Le site de pesage %s n'existe pas dans le reférentiel.", siteName));
        }
        return siteDTO.orElse(null);

    }

    /**
     * getCurrentProduct
     * @return CompletableFuture<Optional<ProduitDTO>>
     */
    @Async
    protected CompletableFuture<Optional<ExploitationDTO>> getCurrentExploitation(List<String> chargement, Map<String, String> mapInverse, List<String> header, List<ExploitationDTO> ref){
        String siteExploitation = chargement.get(header.indexOf(mapInverse.get(environment.getProperty("db.exploitation.nom")))).toUpperCase();
        Optional<ExploitationDTO> exploitationDTO = ref.stream().filter(exploita -> siteExploitation.trim().equals(exploita.getNom())).findFirst();
        if (exploitationDTO.isEmpty()) {
            CustomRecursiveImport.logger.info(String.format("Le site d'explitation %s n'existe pas dans le reférentiel.", siteExploitation));
        }
        return CompletableFuture.completedFuture(exploitationDTO);
    }

    /**
     * getCurrentProduct
     * @return CompletableFuture<Optional<ProduitDTO>>
     */
    @Async
    protected CompletableFuture<Optional<ProduitDTO>> getCurrentProduct(List<String> chargement, Map<String, String> mapInverse, List<String> header,List<ProduitDTO> referentielProduits){
        String nomProduit = chargement.get(header.indexOf(mapInverse.get(environment.getProperty("db.produit.nom")))).toUpperCase();
        Optional<ProduitDTO> produitDTO = referentielProduits.stream().filter(produit -> nomProduit.trim().equals(produit.getNomSRC())).findFirst();
        if (produitDTO.isEmpty()) {
            CustomRecursiveImport.logger.info(String.format("Le produit %s n'existe pas dans le reférentiel.", nomProduit));
        }
        return CompletableFuture.completedFuture(produitDTO);
    }

    /**
     * getCurrentCategorie
     * @return CompletableFuture<Optional<CategorieDTO>>
     */
    @Async
    protected CompletableFuture<Optional<CategorieDTO>> getCurrentCategorie(List<String> chargement, Map<String, String> mapInverse, List<String> header,List<CategorieDTO> ref){
        String categorie = chargement.get(header.indexOf(mapInverse.get(environment.getProperty("db.categorie.type")))).toUpperCase();
        Optional<CategorieDTO> categorieDTO = ref.stream().filter(classe -> categorie.trim().equals(classe.getType())).findFirst();
        if (categorieDTO.isEmpty()) {
            CustomRecursiveImport.logger.info(String.format("La catégorie %s n'existe pas dans le reférentiel.", categorie));
        }
        return CompletableFuture.completedFuture(categorieDTO);
    }

    /**
     * tabToList
     * @param nextLine nextLine
     * @return List
     */
    private static List<String> tabToList(String[] nextLine) {
        return Arrays.stream(nextLine)
                .flatMap(line -> Arrays.stream(line.split(";")))
                .collect(Collectors.toList());
    }
}