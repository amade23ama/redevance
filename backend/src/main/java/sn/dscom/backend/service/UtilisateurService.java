package sn.dscom.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.dto.Credentials;
import sn.dscom.backend.common.dto.UtilisateurConnectedDTO;
import sn.dscom.backend.common.dto.UtilisateurDTO;
import sn.dscom.backend.database.entite.UtilisateurEntity;
import sn.dscom.backend.database.repository.UtilisateurRepository;
import sn.dscom.backend.service.converter.UtilisateurConverter;
import sn.dscom.backend.service.util.TokenUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UtilisateurService {
    private UtilisateurRepository utilisateurRepository;

    private UtilisateurEntity chargerUtilisateurFromDatabase(UtilisateurDTO utilisateurDTO) {
        //return utilisateurRepository.getReferenceById();
        // todo
        return null;
    }
    @Transactional
    public  UtilisateurDTO sauvegarderUtilisateur(UtilisateurDTO utilisateurDTO){
      return  miseaJourUtilisateur(utilisateurDTO);
    }

    /**
     * pour active ou desactive un utilisateur
     * @param utilisateurDTO
     * @return
     */
    @Transactional
    public  UtilisateurDTO activeOrDesactiveUtilisateur(UtilisateurDTO utilisateurDTO){
        UtilisateurEntity userDetail = utilisateurRepository.findById(utilisateurDTO.getId()).orElse(null);
        if(userDetail!=null){
            userDetail.setActive(utilisateurDTO.isActive());
            userDetail=utilisateurRepository.save(userDetail);
        }
       return UtilisateurConverter.toUtilisateurDTO(userDetail);
    }
    @Transactional
    public  List<UtilisateurDTO> getAllUtilisateurs(){
      List<UtilisateurEntity> users = utilisateurRepository.findAll();
        List<UtilisateurDTO>  utilisateurDTOList=users.stream()
                .map(u->UtilisateurConverter.toUtilisateurDTO(u))
                .collect(Collectors.toList());

        return utilisateurDTOList;
    }

    private UtilisateurDTO miseaJourUtilisateur(UtilisateurDTO utilisateurDTO){
        UtilisateurEntity user= UtilisateurConverter.toUtilisateurEntity(utilisateurDTO);
        UtilisateurEntity userDetail = utilisateurRepository.findById(utilisateurDTO.getId()).orElse(null);
        if(userDetail!=null){
            user.setDateModification(new Date());
            user.setId(userDetail.getId());
            userDetail= utilisateurRepository.save(user);
        }else {
            user.setId(null);
            user.setDateCreation(new Date());
            userDetail= utilisateurRepository.save(user);
        }
        return UtilisateurConverter.toUtilisateurDTO(userDetail);
    }
}
