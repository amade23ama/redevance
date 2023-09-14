package sn.dscom.backend.service;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.constants.Enum.ErreurEnum;
import sn.dscom.backend.common.dto.AutocompleteRecherche;
import sn.dscom.backend.common.dto.CritereRecherche;
import sn.dscom.backend.common.dto.UtilisateurDTO;
import sn.dscom.backend.common.exception.CommonMetierException;
import sn.dscom.backend.common.util.Utils;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.ProfilEntity;
import sn.dscom.backend.database.entite.UtilisateurEntity;
import sn.dscom.backend.database.repository.UtilisateurRepository;
import sn.dscom.backend.service.converter.UtilisateurConverter;
import sn.dscom.backend.service.interfaces.IUtilisateurService;
import sn.dscom.backend.service.mail.EmailDetails;
import sn.dscom.backend.service.mail.IMailService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UtilisateurService implements IUtilisateurService {

    /** Logger Factory */
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilisateurService.class);

    /** utilisateur Transformer */
    private final Transformer<UtilisateurDTO, UtilisateurEntity> utilisateurTransformer = new UtilisateurConverter();

    /** utilisateur Repository*/
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    /** mailService */
    @Autowired
    private IMailService mailService;

    /**
     * sauvegarderUtilisateur
     * @param utilisateurDTO utilisateurDTO
     * @return UtilisateurDTO
     */
    public UtilisateurDTO sauvegarderUtilisateur(UtilisateurDTO utilisateurDTO) {

        //Dans le cas d'une modification
        if(utilisateurDTO.getId()!=null){
            Optional<UtilisateurEntity> user = utilisateurRepository.findById(utilisateurDTO.getId()) ;
            if(user.isPresent()){
                UtilisateurEntity utilisateurEntity=user.get();
                UtilisateurConverter.majUtilisateurDepuisDTO(utilisateurDTO,utilisateurEntity);
                UtilisateurEntity userSave = utilisateurRepository.save(utilisateurEntity);
                return UtilisateurConverter.toUtilisateurDTO(userSave);
            }
            throw new CommonMetierException(HttpStatus.NOT_ACCEPTABLE.value(), ErreurEnum.ERR_CONTRAT_NOT_FOUND);
        }

        // On génére le login
        String login = Utils.generateLogin(utilisateurDTO);
        int i = 1;
        while (loginExiste(login)) {
            login = login + i;
            i++;
        }
        utilisateurDTO.setLogin(login);
        String mdp = UUID.randomUUID().toString().substring(0, 8);
        utilisateurDTO.setPassword(mdp);
        if (utilisateurDTO.getId() == null) {
            Optional<UtilisateurEntity> userFinded = utilisateurRepository.findUtilisateurEntityByTelephoneEquals(utilisateurDTO.getTelephone());

            if (userFinded.isPresent()) {
                throw new CommonMetierException(HttpStatus.NOT_ACCEPTABLE.value(), ErreurEnum.ERR_CONTRAT_NOT_FOUND);
            }
            utilisateurDTO.setActive(utilisateurDTO.isActive());
            UtilisateurEntity user = Try.of(() -> UtilisateurConverter.toUtilisateurEntity(utilisateurDTO))
                        .mapTry(utilisateurRepository::save)
                        .getOrElseGet(ex -> UtilisateurEntity.builder().build());

            UtilisateurService.LOGGER.info(String.format("l'utilisateur %s a été créé.", user.getLogin()));

            //Envoi du mail
            if (!Strings.isNullOrEmpty(user.getLogin())) {
                String msgBody = """
                            Bonjour,
                            Voici votre mot de passe: 
                            """ + mdp + " et votre login: " + login;

                    // Envoi du méssage
                this.mailService.envoiMail(EmailDetails.builder()
                            .recipient(user.getEmail())
                            .subject("Mail de confirmation")
                            .msgBody(msgBody)
                            .build());
                }
            return utilisateurTransformer.reverse(user);
        }
        return null;
    }

    /**
     * mjUtilisateur
     * @param utilisateurDTO utilisateurDTO
     * @return UtilisateurDTO
     */
    public UtilisateurDTO mjUtilisateur(UtilisateurDTO utilisateurDTO) {
        return miseaJourUtilisateur(utilisateurDTO);
    }

    /**
     * pour active ou desactive un utilisateur
     *
     * @param utilisateurDTO utilisateurDTO
     * @return UtilisateurDTO
     */
    public UtilisateurDTO activeOrDesactiveUtilisateur(UtilisateurDTO utilisateurDTO) {
        UtilisateurEntity userDetail = utilisateurRepository.findById(utilisateurDTO.getId()).orElse(null);
        if (userDetail != null) {
            userDetail.setActive(utilisateurDTO.isActive());
            userDetail = utilisateurRepository.save(userDetail);
        }
        return utilisateurTransformer.reverse(userDetail);
    }

    /**
     * getAllUtilisateurs
     * @return list
     */
    public List<UtilisateurDTO> getAllUtilisateurs() {
        List<UtilisateurEntity> users = utilisateurRepository.findAll();
        return users.stream()
                .map(UtilisateurConverter::toUtilisateurDTO)
                .collect(Collectors.toList());
    }

    /**
     * miseaJourUtilisateur
     * @param utilisateurDTO UtilisateurDTO
     * @return UtilisateurDTO
     */
    private UtilisateurDTO miseaJourUtilisateur(UtilisateurDTO utilisateurDTO) {
        UtilisateurEntity user = UtilisateurConverter.toUtilisateurEntity(utilisateurDTO);
        UtilisateurEntity userDetail = utilisateurRepository.findById(utilisateurDTO.getId()).orElse(null);
        if (userDetail != null) {
            user.setDateModification(new Date());
            user.setId(userDetail.getId());
            userDetail = utilisateurRepository.save(user);
        } else {
            user.setId(null);
            user.setDateCreation(new Date());
            userDetail = utilisateurRepository.save(user);
        }
        return utilisateurTransformer.reverse(userDetail);
    }

    /**
     * chargerUtilisateur
     * @param email email
     * @return UtilisateurDTO
     */
    public UtilisateurDTO chargerUtilisateur(String email) {
        UtilisateurEntity userDetail = utilisateurRepository.findUtilisateurEntitiesByLoginExists(email);
        return utilisateurTransformer.reverse(userDetail);
    }

    /**
     * loginExiste
     * @param login login
     * @return true or false
     */
    private boolean loginExiste(String login) {
        Integer val = this.utilisateurRepository.checkUtilisateurEntitiesByLoginExists(login);
        return val > 0;
    }

    /**
     * chargerUtilisateurParId
     * @param id id
     * @return UtilisateurDTO
     */
    public UtilisateurDTO chargerUtilisateurParId(Long id) {
        Optional<UtilisateurEntity> user = utilisateurRepository.findById(id);
        if (user.isPresent()) {
            return utilisateurTransformer.reverse(user.get());
        } else {
            throw new CommonMetierException(HttpStatus.NOT_ACCEPTABLE.value(), ErreurEnum.ERR_CONTRAT_NOT_FOUND);
        }
    }

    /**
     * checkEmail
     * @param id id
     * @param email email
     * @return  true or false
     */
    public boolean checkEmail(Long id,String email) {
        Integer nb= utilisateurRepository.checkEmailExists(email,id);
        return nb > 0;
    }

    /**
     * checkEmail
     * @param email email
     * @return  true or false
     */
    public boolean checkEmail(String email) {
        Integer nb= utilisateurRepository.checkEmailExists(email);
        return nb > 0;
    }

    @Override
    public List<UtilisateurDTO> rechargementParCritere(CritereRecherche<?> critereRecherche) {
        List<Long> idUtilisateur = new ArrayList<>();
        List<String> idProfils=new ArrayList<>();
             idProfils.addAll(critereRecherche.getAutocompleteRecherches().stream()
                     .filter(item -> item instanceof AutocompleteRecherche)
                     .filter(item -> ((AutocompleteRecherche) item).getTypeClass() == ProfilEntity.class)
                     .map(item -> String.valueOf(((AutocompleteRecherche) item).getId()))
                     .collect(Collectors.toList()));
             idUtilisateur.addAll(critereRecherche.getAutocompleteRecherches().stream()
                     .filter(item -> item instanceof AutocompleteRecherche)
                     .filter(item -> ((AutocompleteRecherche) item).getTypeClass() == UtilisateurEntity.class)
                     .map(item ->  Long.parseLong(((AutocompleteRecherche) item).getId().toString()))
                     .collect(Collectors.toList()));

        List<UtilisateurEntity> users = utilisateurRepository
                     .rechargementParCritere(idUtilisateur.isEmpty()?null:idUtilisateur ,
                             idProfils.isEmpty()?null:idProfils);
             return users.stream()
                     .map(UtilisateurConverter::toUtilisateurDTO)
                     .collect(Collectors.toList());

    }
}
