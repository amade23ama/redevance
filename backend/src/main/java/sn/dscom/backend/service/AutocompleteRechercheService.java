package sn.dscom.backend.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.dto.AutocompleteRecherche;
import sn.dscom.backend.database.entite.ProfilEntity;
import sn.dscom.backend.database.entite.UtilisateurEntity;
import sn.dscom.backend.database.repository.ProfilRepository;
import sn.dscom.backend.database.repository.UtilisateurRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class AutocompleteRechercheService {
    public final Pageable defaultLimit = PageRequest.of(0, 200);
    /** utilisateur Repository*/
    private UtilisateurRepository utilisateurRepository;
    private ProfilRepository profilRepository;
    @Autowired
    public AutocompleteRechercheService(UtilisateurRepository utilisateurRepository,
                                        ProfilRepository profilRepository) {
        this.profilRepository=profilRepository;
        this.utilisateurRepository=utilisateurRepository;
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


}
