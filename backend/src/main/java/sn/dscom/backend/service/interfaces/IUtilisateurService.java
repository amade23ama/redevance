package sn.dscom.backend.service.interfaces;

import org.springframework.web.bind.annotation.RequestBody;
import sn.dscom.backend.common.dto.CritereRecherche;
import sn.dscom.backend.common.dto.UtilisateurDTO;

import java.util.List;

/**
 * Interface Utilisateur Service
 */
public interface IUtilisateurService {

    /**
     * sauvegarder Utilisateur
     *
     * @param utilisateurDTO utilisateurDTO
     * @return utilisateurDTO
     */
    UtilisateurDTO sauvegarderUtilisateur(UtilisateurDTO utilisateurDTO);

    /**
     * mj Utilisateur
     *
     * @param utilisateurDTO utilisateurDTO
     * @return UtilisateurDTO
     */
    UtilisateurDTO mjUtilisateur(UtilisateurDTO utilisateurDTO);

    /**
     * pour active ou desactive un utilisateur
     *
     * @param utilisateurDTO
     * @return
     */
     UtilisateurDTO activeOrDesactiveUtilisateur(UtilisateurDTO utilisateurDTO);

    /**
     * get All Utilisateurs
     * @return all
     */
    List<UtilisateurDTO> getAllUtilisateurs();

    /**
     * charger Utilisateur
     *
     * @param email email
     * @return UtilisateurDTO
     */
     UtilisateurDTO chargerUtilisateur(String email);

    /**
     * charger Utilisateur Par Id
     * @param id id
     * @return UtilisateurDTO
     */
     UtilisateurDTO chargerUtilisateurParId(Long id);

    /**
     * check Email
     * @param id id
     * @param email email
     * @return boolean
     */
     boolean checkEmail(Long id, String email);

    /**
     * check Email
     * @param email email
     * @return boolean
     */
     boolean checkEmail(String email);

    public List<UtilisateurDTO> rechargementParCritere(CritereRecherche<?> critereRecherche);

    /**
     * findUserWithEmailAndLogin
     * @param utilisateurDTO utilisateurDTO
     * @return UtilisateurDTO
     */
    UtilisateurDTO findUserWithEmailAndLogin(UtilisateurDTO utilisateurDTO);
}
