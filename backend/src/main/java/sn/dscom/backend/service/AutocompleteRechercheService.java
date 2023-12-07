package sn.dscom.backend.service;

import com.google.common.base.Strings;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import io.vavr.control.Try;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.dto.AutocompleteRecherche;
import sn.dscom.backend.database.entite.*;
import sn.dscom.backend.database.repository.*;
import sn.dscom.backend.service.interfaces.IAutocompleteRechercheService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
public class AutocompleteRechercheService implements IAutocompleteRechercheService {
    public final Pageable defaultLimit = PageRequest.of(0, 200);

    /** utilisateur Repository*/
    private final UtilisateurRepository utilisateurRepository;

    /** profil Repository */
    private final ProfilRepository profilRepository;

    /** produit Repository */
    private final ProduitRepository produitRepository;

    /** site Repository */
    private final SiteRepository siteRepository;

    /** exploitation Repository */
    private final ExploitationRepository exploitationRepository;

    /** vehicule Repository */
    private final VehiculeRepository vehiculeRepository;

    /** chargement Repository */
    private ChargementRepository chargementRepository;

    private CategorieRepository categorieRepository;

    private DepotRepository depotRepository;
    private  TransporteurRepository transporteurRepository;

    /** matcherGlobal */
    private final ExampleMatcher matcherGlobal = ExampleMatcher.matchingAny()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
            .withIgnoreCase()
            .withIgnoreNullValues();

    /**
     * AutocompleteRechercheService
     * @param utilisateurRepository utilisateurRepository
     * @param profilRepository profilRepository
     */
    @Builder
    public AutocompleteRechercheService(UtilisateurRepository utilisateurRepository, ProduitRepository produitRepository, ExploitationRepository exploitationRepository,
                                        ProfilRepository profilRepository, SiteRepository siteRepository, VehiculeRepository vehiculeRepository,
                                        ChargementRepository chargementRepository,
                                        CategorieRepository categorieRepository, DepotRepository depotRepository,TransporteurRepository transporteurRepository) {
        this.profilRepository=profilRepository;
        this.utilisateurRepository=utilisateurRepository;
        this.produitRepository=produitRepository;
        this.siteRepository=siteRepository;
        this.exploitationRepository=exploitationRepository;
        this.vehiculeRepository=vehiculeRepository;
        this.chargementRepository=chargementRepository;
        this.categorieRepository=categorieRepository;
        this.depotRepository=depotRepository;
        this.transporteurRepository=transporteurRepository;


    }
    @Transactional(readOnly = true)
    public List<AutocompleteRecherche> getUtilisateurAutocompleteRecherche(String capture) {
        List<AutocompleteRecherche> lest2=new ArrayList<>();
        if (capture != null || capture != "") {
        UtilisateurEntity utilisateur = new UtilisateurEntity();
        utilisateur.setNom(capture);
        utilisateur.setPrenom(capture);
        utilisateur.setTelephone(capture);
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnoreNullValues();
        Sort sort = Sort.by(Sort.Direction.ASC, "username");
        List<UtilisateurEntity> list=this.utilisateurRepository.findAll(Example.of(utilisateur, matcher));
        if(!list.isEmpty()){
            lest2  =list.stream().map(AutocompleteRecherche::new).collect(Collectors.toList());
        }
        }
        return lest2;

    }
    @Transactional(readOnly = true)
    public List<AutocompleteRecherche> getProfilAutocompleteRecherche(String capture) {
        List<AutocompleteRecherche> lest2=new ArrayList<>();
        ProfilEntity profil = new ProfilEntity();
        if (capture != null || capture != "") {
            profil.setLibelle(capture);
            profil.setCode(capture);
            ExampleMatcher matcher = ExampleMatcher.matchingAny()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                    .withIgnoreCase()
                    .withIgnoreNullValues();
            List<ProfilEntity> list = this.profilRepository.findAll(Example.of(profil, matcher));
            if (!list.isEmpty()) {
          lest2 = list.stream().map(AutocompleteRecherche::new).collect(Collectors.toList());

            }
            return lest2;
        }
        return null;

    }

    /**
     * getProduitAutocompleteRecherche
     *
     * @param capture capture
     * @return la liste
     */
    @Override
    public List<AutocompleteRecherche> getProduitsAutocompleteRecherche(String capture) {

        // si null or empty, on lève une 404
        if (!Strings.isNullOrEmpty(capture)) {
            List<ProduitEntity> list = Try.of(() -> ProduitEntity.builder()
                                                                .nomSRC(capture)
                                                                .nomNORM(capture)
                                                                .build())
                    .map(produitAchercher -> this.produitRepository.findAll(Example.of(produitAchercher, matcherGlobal)))
                    .getOrElse(ArrayList::new);

            if(!list.isEmpty()){
                return  list.stream().map(AutocompleteRecherche::new).collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }

    /**
     * getSitesPessageAutocompleteRecherche
     *
     * @param capture capture
     * @return la liste
     */
    @Override
    public List<AutocompleteRecherche> getSitesPessageAutocompleteRecherche(String capture) {
        // si null or empty, on lève une 404
        String captureToNumber=null;
        final List<AutocompleteRecherche> listAutocompleteRecherche = new ArrayList<>();
        if (!Strings.isNullOrEmpty(capture)) {

            try {
                captureToNumber=capture.replaceAll("\\s", "");
                double doubleNum = Double.parseDouble(captureToNumber);

            }catch (Exception e){
                listAutocompleteRecherche.add(new AutocompleteRecherche(captureToNumber, "LOCALITE",captureToNumber, "Localite"));
            }

            List<SiteEntity> list = Try.of(() -> SiteEntity.builder()
                            .nom(capture)
                            .localite(capture)
                            .build())
                    .map(sitesAchercher -> this.siteRepository.findAll(Example.of(sitesAchercher, matcherGlobal)))
                    .getOrElse(ArrayList::new);

            if(!list.isEmpty()){
              listAutocompleteRecherche.addAll(list.stream().map(AutocompleteRecherche::new).collect(Collectors.toList()));
            }
        }
        return  listAutocompleteRecherche;
    }

    /**
     * getSitesExploitationAutocompleteRecherche
     *
     * @param capture capture
     * @return la liste
     */
    @Override
    public List<AutocompleteRecherche> getSitesExploitationAutocompleteRecherche(String capture) {
        // si null or empty, on lève une 404
        String captureToNumber=null;
        final List<AutocompleteRecherche> listAutocompleteRecherche = new ArrayList<>();
        if (!Strings.isNullOrEmpty(capture)) {

            try {
                captureToNumber=capture.replaceAll("\\s", "");
                double doubleNum = Double.parseDouble(captureToNumber);

            }catch (Exception e){
                listAutocompleteRecherche.add(new AutocompleteRecherche(captureToNumber, "REGION",captureToNumber, "REGION"));
            }



            List<ExploitationEntity> list = Try.of(() -> ExploitationEntity.builder()
                            .nom(capture)
                            .region(capture)
                            .build())
                    .map(sitesExploitationAchercher -> this.exploitationRepository.findAll(Example.of(sitesExploitationAchercher, matcherGlobal)))
                    .getOrElse(ArrayList::new);

            if(!list.isEmpty()){
                listAutocompleteRecherche.addAll(list.stream().map(AutocompleteRecherche::new).collect(Collectors.toList()));

            }
        }
        return listAutocompleteRecherche;
    }

    /**
     * getVoitureAutocompleteRecherche
     *
     * @param capture capture
     * @return la liste
     */
    @Override
    public List<AutocompleteRecherche> getVoitureAutocompleteRecherche(String capture) {
        // si null or empty, on lève une 404
        if (!Strings.isNullOrEmpty(capture)) {
            List<VehiculeEntity> list = Try.of(() -> VehiculeEntity.builder()
                            .immatriculation(capture)
                            .build())
                    .map(vehicules -> this.vehiculeRepository.findAll(Example.of(vehicules, matcherGlobal)))
                    .getOrElse(ArrayList::new);

            if(!list.isEmpty()){
                return  list.stream().map(AutocompleteRecherche::new).collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }

    /**
     * getChargementAutocompleteRecherche
     *
     * @param capture capture
     * @return la liste
     */
    @Override
    public List<AutocompleteRecherche> getChargementAutocompleteRecherche(String capture) {
        // si null or empty, on lève une 404
        final List<AutocompleteRecherche> listAutocompleteRecherche = new ArrayList<>();
        if (!Strings.isNullOrEmpty(capture)) {
            try {
                String captureToNumber=capture.replaceAll("\\s", "");
                double doubleNum = Double.parseDouble(captureToNumber);
                listAutocompleteRecherche.add(new AutocompleteRecherche(captureToNumber, "N° Import",captureToNumber, "Import"));
            }catch (Exception e){

            }
        }

        return  listAutocompleteRecherche;


        /*if (!Strings.isNullOrEmpty(capture)) {
            List<ChargementEntity> list = Try.of(() -> ChargementEntity.builder()
                            .destination(capture)
                            .build())
                    .map(chargement -> this.chargementRepository.findAll(Example.of(chargement, matcherGlobal)))
                    .getOrElse(ArrayList::new);

            if(!list.isEmpty()){
                return  list.stream().map(AutocompleteRecherche::new).collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
        */
    }

    @Override
    public List<AutocompleteRecherche> getCategorieAutocompleteRecherche(String capture) {
        final List<AutocompleteRecherche> listAutocompleteRecherche = new ArrayList<>();
        CategorieEntity categorie = new CategorieEntity();
        // si null or empty, on lève une 404
        if (!Strings.isNullOrEmpty(capture)) {
            categorie.setType(capture);
            try {
                String captureToNumber=capture.replaceAll("\\s", "");
                double doubleNum = Double.parseDouble(captureToNumber);
                listAutocompleteRecherche.add(new AutocompleteRecherche(captureToNumber, "VOLUME",captureToNumber, "VOLUME"));
            }catch (Exception e){

            }

            List<CategorieEntity> list = this.categorieRepository.findAll(Example.of(categorie, matcherGlobal));

            if(!list.isEmpty()){
                listAutocompleteRecherche.addAll(list.stream().map(AutocompleteRecherche::new).collect(Collectors.toList()));
            }
        }
        return listAutocompleteRecherche;
    }

    /**
     * @param capture capture
     * @return capture
     */
    @Override
    public List<AutocompleteRecherche> getDepotAutocompleteRecherche(String capture) {
        if (!Strings.isNullOrEmpty(capture)) {
            List<DepotEntity> list = Try.of(() -> DepotEntity.builder()
                            .nom(capture)
                            .build())
                    .map(depot -> this.depotRepository.findAll(Example.of(depot, matcherGlobal)))
                    .getOrElse(ArrayList::new);

            if(!list.isEmpty()){
                return  list.stream().map(AutocompleteRecherche::new).collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }


}
