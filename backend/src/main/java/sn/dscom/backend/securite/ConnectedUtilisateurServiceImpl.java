package sn.dscom.backend.securite;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import sn.dscom.backend.common.dto.Credentials;
import sn.dscom.backend.common.dto.UtilisateurConnectedDTO;
import sn.dscom.backend.database.entite.UtilisateurEntity;
import sn.dscom.backend.database.repository.UtilisateurRepository;
import sn.dscom.backend.service.ConnectedUtilisateurService;
import sn.dscom.backend.service.converter.UtilisateurConverter;
import sn.dscom.backend.service.util.TokenUtils;

@Service
@Transactional(value = Transactional.TxType.REQUIRED,rollbackOn = Exception.class)
public class ConnectedUtilisateurServiceImpl implements ConnectedUtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

   @Autowired
    public ConnectedUtilisateurServiceImpl(UtilisateurRepository utilisateurRepository){
        this.utilisateurRepository=utilisateurRepository;
    }
    /**
     * Renvoi l'utilisateur connecté.
     *
     * @return utilisateur connecté
     */
    @Override
    public UtilisateurConnectedDTO getConnectedUtilisateur() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        UtilisateurConnectedDTO userDTO = (UtilisateurConnectedDTO) ctx.getAuthentication().getPrincipal();
        UtilisateurConnectedDTO utilisateurConnectedDTO= getUtilisateurByLogin(userDTO.getLogin());
        return utilisateurConnectedDTO;

    }

    /**
     * Renvoi l'id de l'utilisateur connecté.
     *
     * @return id de l'utilisateur connecté
     */
    @Override
    public String getConnectedUserId() {
        return null;
    }

    /**
     * Renvoi l'id de l'utilisateur  connecté.
     *
     * @return id de l'utilisateur  connecté
     */
    @Override
    public Long getConnectedUtilisateurId() {
        Long userId = null;
       UtilisateurConnectedDTO  utilisateurConnected = getConnectedUtilisateur();
        if (utilisateurConnected!=null) {
            userId = utilisateurConnected.getId();
        }
        return userId;
    }

    /**
     * Renvoi l'utilisateur détail connecté.
     *
     * @return utilisateur détail connecté
     */
    @Override
    public UtilisateurEntity chargerUtilisateur() {
        return null;
    }

    @Override
    public String genereTokenAuthentificationUtilisateur(Credentials credentials) {
        UtilisateurConnectedDTO utilisateurConnectedDTO= getUtilisateurByLogin(credentials.getLogin());
        String token = TokenUtils.generateToken(utilisateurConnectedDTO);
        return token;
    }
    private UtilisateurConnectedDTO getUtilisateurByLogin(String login){
        UtilisateurEntity  user=  utilisateurRepository.findUtilisateurEntitiesByLoginExists(login);
        UtilisateurConnectedDTO utilisateurConnectedDTO= UtilisateurConverter.toUtilisateurConnectedDTO(user);
        return utilisateurConnectedDTO;
    }


}
