package sn.dscom.backend.service.converter;

import com.google.common.base.Strings;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.CollectionUtils;
import sn.dscom.backend.common.dto.UtilisateurConnectedDTO;
import sn.dscom.backend.common.dto.UtilisateurDTO;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.ProfilEntity;
import sn.dscom.backend.database.entite.UtilisateurEntity;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utilisateur Converter
 */
public class UtilisateurConverter implements Transformer<UtilisateurDTO, UtilisateurEntity> {

    public static UtilisateurDTO toUtilisateurDTO(@Valid UtilisateurEntity utilisateurEntity) {
        //todo
        if (utilisateurEntity == null) {
            return null;
        }
         UtilisateurDTO utilisateurDTO=UtilisateurDTO.builder()
                .id(utilisateurEntity.getId())
                .nom(utilisateurEntity.getNom())
                .prenom(utilisateurEntity.getPrenom())
                .login(utilisateurEntity.getLogin())
                .password(utilisateurEntity.getPassword())
                .email(utilisateurEntity.getEmail())
                .telephone(utilisateurEntity.getTelephone())
                .active(utilisateurEntity.getId()==null?true:utilisateurEntity.isActive())
                .dateCreation(utilisateurEntity.getId() == null ? new Date() :utilisateurEntity.getDateCreation())
                .dateModification(utilisateurEntity == null ? null :utilisateurEntity.getDateModification())
                //.depotEntityListDTO(null)
                .build();
        List<String> listeProfils = new ArrayList<>();
       if (!CollectionUtils.isEmpty(utilisateurEntity.getProfils())) {
            utilisateurEntity.getProfils()
                    .forEach((ProfilEntity profilEntity) ->
                            listeProfils.add(profilEntity.getCode()));
        }
        utilisateurDTO.setProfils(listeProfils);

        /*List<ProfilDTO> listeProfils = new ArrayList<>();
        if (!CollectionUtils.isEmpty(utilisateurEntity.getProfils())) {
            utilisateurEntity.getProfils()
                    .forEach((ProfilEntity profilEntity) ->
                            listeProfils.add(ProfilConverter.toProfilDTO(profilEntity)));
        }
        utilisateurDTO.setProfils(listeProfils);
        */
        return utilisateurDTO;
    }
    public static UtilisateurEntity toUtilisateurEntity( UtilisateurDTO utilisateurDTO) {
        //todo
        if (utilisateurDTO == null) {
            return null;
        }
        UtilisateurEntity utilisateurEntity =UtilisateurEntity.builder()
                .id(utilisateurDTO.getId())
                .login(utilisateurDTO.getLogin())
                .email(utilisateurDTO.getEmail())
                .password(utilisateurDTO.getPassword())
                .prenom(utilisateurDTO.getPrenom())
                .nom(utilisateurDTO.getNom())
                .telephone(utilisateurDTO.getTelephone())
                .active(utilisateurDTO.isActive())
                .dateCreation(utilisateurDTO.getId() == null ? new Date() :utilisateurDTO.getDateCreation())
                .dateModification(utilisateurDTO.getId() == null? null :utilisateurDTO.getDateModification())
                .build();


        /*final List<ProfilEntity> listeProfils = new ArrayList<>();
        if (!CollectionUtils.isEmpty(utilisateurDTO.getProfils())) {
            utilisateurDTO.getProfils()
                    .forEach((ProfilDTO profilDTO) ->
                            listeProfils.add(ProfilConverter.toProfilEntity(profilDTO)));
        }
        */
        final List<ProfilEntity> listeProfils = new ArrayList<>();
        if (!CollectionUtils.isEmpty(utilisateurDTO.getProfils())) {
            utilisateurDTO.getProfils()
                    .forEach((String val) ->
                            listeProfils.add(ProfilEntity.builder().code(val).build()));
        }

        utilisateurEntity.setProfils(listeProfils);


        return utilisateurEntity;
    }
    public static UtilisateurConnectedDTO toUtilisateurConnectedDTO(@Valid UtilisateurEntity utilisateurEntity){
        if (utilisateurEntity == null) {
            return null;
        }
        UtilisateurConnectedDTO utilisateurConnectedDTO = UtilisateurConnectedDTO.builder()
                .email(utilisateurEntity.getEmail())
                .id(utilisateurEntity.getId())
                .prenom(utilisateurEntity.getPrenom())
                .nom(utilisateurEntity.getNom())
                .login(utilisateurEntity.getLogin())
                .build();

        Collection<? extends GrantedAuthority> authorities = utilisateurEntity.getProfils().stream()
                .map(role -> new SimpleGrantedAuthority(role.getCode()))
                .collect(Collectors.toList());
        utilisateurConnectedDTO.setAuthorities(authorities);
        return utilisateurConnectedDTO;

    }
    public static void majUtilisateurDepuisDTO(UtilisateurDTO utilisateurDTO,UtilisateurEntity utilisateurEntity){
        utilisateurEntity.setActive(utilisateurDTO.isActive());
        utilisateurEntity.setNom(utilisateurDTO.getNom());
        utilisateurEntity.setPrenom(utilisateurDTO.getPrenom());
        utilisateurEntity.setEmail(utilisateurDTO.getEmail());
        utilisateurEntity.setTelephone(utilisateurDTO.getTelephone());

        // On l'aliment seulement en cas de modification de mdp
        if (!Strings.isNullOrEmpty(utilisateurDTO.getPassword())) {
            utilisateurEntity.setPassword(utilisateurDTO.getPassword());
        }

        final List<ProfilEntity> listeProfils = new ArrayList<>();
        if (!CollectionUtils.isEmpty(utilisateurDTO.getProfils())) {
            utilisateurDTO.getProfils()
                    .forEach((String val) ->
                            listeProfils.add(ProfilEntity.builder().code(val).build()));
        }
        utilisateurEntity.setProfils(listeProfils);
    }

    /**
     * transformation de {@link UtilisateurEntity} en {@link UtilisateurDTO}
     *
     * @param utilisateurEntity l'objet à transformer en {@link UtilisateurDTO}
     * @return l'objet {@link UtilisateurDTO}
     */
    @Override
    public UtilisateurDTO reverse(UtilisateurEntity utilisateurEntity) {
        //todo
        if (utilisateurEntity == null) {
            return null;
        }
        UtilisateurDTO utilisateurDTO=UtilisateurDTO.builder()
                .id(utilisateurEntity.getId())
                .nom(utilisateurEntity.getNom())
                .prenom(utilisateurEntity.getPrenom())
                .login(utilisateurEntity.getLogin())
                .password(utilisateurEntity.getPassword())
                .email(utilisateurEntity.getEmail())
                .telephone(utilisateurEntity.getTelephone())
                .active(utilisateurEntity.getId()==null?true:utilisateurEntity.isActive())
                .dateCreation(utilisateurEntity.getId() == null ? new Date() :utilisateurEntity.getDateCreation())
                .dateModification(utilisateurEntity == null ? null :utilisateurEntity.getDateModification())
                //.depotEntityListDTO(null)
                .build();
        List<String> listeProfils = new ArrayList<>();
        if (!CollectionUtils.isEmpty(utilisateurEntity.getProfils())) {
            utilisateurEntity.getProfils()
                    .forEach((ProfilEntity profilEntity) ->
                            listeProfils.add(profilEntity.getCode()));
        }
        utilisateurDTO.setProfils(listeProfils);

        /*List<ProfilDTO> listeProfils = new ArrayList<>();
        if (!CollectionUtils.isEmpty(utilisateurEntity.getProfils())) {
            utilisateurEntity.getProfils()
                    .forEach((ProfilEntity profilEntity) ->
                            listeProfils.add(ProfilConverter.toProfilDTO(profilEntity)));
        }
        utilisateurDTO.setProfils(listeProfils);
        */
        return utilisateurDTO;
    }

    /**
     * transformation de {@link UtilisateurDTO} en {@link UtilisateurEntity}
     *
     * @param utilisateurDTO l'objet à transformer en {@link UtilisateurEntity}
     * @return l'objet {@link UtilisateurEntity}
     */
    @Override
    public UtilisateurEntity transform(UtilisateurDTO utilisateurDTO) {
        //todo
        if (utilisateurDTO == null) {
            return null;
        }
        UtilisateurEntity utilisateurEntity =UtilisateurEntity.builder()
                .id(utilisateurDTO.getId())
                .login(utilisateurDTO.getLogin())
                .email(utilisateurDTO.getEmail())
                .password(utilisateurDTO.getPassword())
                .prenom(utilisateurDTO.getPrenom())
                .nom(utilisateurDTO.getNom())
                .telephone(utilisateurDTO.getTelephone())
                .active(utilisateurDTO.getId()==null?true:utilisateurDTO.isActive())
                .dateCreation(utilisateurDTO.getId() == null ? new Date() :utilisateurDTO.getDateCreation())
                .dateModification(utilisateurDTO.getId() == null? null :utilisateurDTO.getDateModification())
                .build();


        /*final List<ProfilEntity> listeProfils = new ArrayList<>();
        if (!CollectionUtils.isEmpty(utilisateurDTO.getProfils())) {
            utilisateurDTO.getProfils()
                    .forEach((ProfilDTO profilDTO) ->
                            listeProfils.add(ProfilConverter.toProfilEntity(profilDTO)));
        }
        */
        final List<ProfilEntity> listeProfils = new ArrayList<>();
        if (!CollectionUtils.isEmpty(utilisateurDTO.getProfils())) {
            utilisateurDTO.getProfils()
                    .forEach((String val) ->
                            listeProfils.add(ProfilEntity.builder().code(val).build()));
        }

        utilisateurEntity.setProfils(listeProfils);


        return utilisateurEntity;
    }

    /**
     *  convert UtilisateurConnectedDTO en UtilisateurDTO
     *
     * @param utilisateurConnectedDTO utilisateurConnectedDTO
     * @return UtilisateurDTO
     */
    public static UtilisateurDTO toUtilisateurDTO(@Valid UtilisateurConnectedDTO utilisateurConnectedDTO){

        // si l'objet est null
        if (utilisateurConnectedDTO == null) {
            return null;
        }

        UtilisateurDTO utilisateurDTO = UtilisateurDTO.builder()
                .email(utilisateurConnectedDTO.getEmail())
                .id(utilisateurConnectedDTO.getId())
                .prenom(utilisateurConnectedDTO.getPrenom())
                .nom(utilisateurConnectedDTO.getNom())
                .login(utilisateurConnectedDTO.getLogin())
                .build();

        Collection<? extends GrantedAuthority> authorities = utilisateurConnectedDTO.getAuthorities().stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());
        utilisateurConnectedDTO.setAuthorities(authorities);

        return utilisateurDTO;
    }
}
