package sn.dscom.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.constants.Enum.ErreurEnum;
import sn.dscom.backend.common.dto.Credentials;
import sn.dscom.backend.common.dto.UtilisateurConnectedDTO;
import sn.dscom.backend.common.dto.UtilisateurDTO;
import sn.dscom.backend.common.exception.CommonMetierException;
import sn.dscom.backend.common.util.Utils;
import sn.dscom.backend.database.entite.UtilisateurEntity;
import sn.dscom.backend.database.repository.UtilisateurRepository;
import sn.dscom.backend.service.converter.UtilisateurConverter;
import sn.dscom.backend.service.util.TokenUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    public UtilisateurDTO sauvegarderUtilisateur(UtilisateurDTO utilisateurDTO) {
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
        if (utilisateurDTO.getEmail().isEmpty() || utilisateurDTO.getPrenom().isEmpty() || utilisateurDTO.getNom().isEmpty()) {
            return null;
        }
        String login = Utils.generateLogin(utilisateurDTO);
        int i = 1;
        while (loginExiste(login)) {
            login = login + i;
            i++;
        }
        utilisateurDTO.setLogin(login);
        utilisateurDTO.setPassword(login);
        if (utilisateurDTO.getId() == null) {
            utilisateurDTO.setActive(utilisateurDTO.isActive());
            UtilisateurEntity user = utilisateurRepository.save(UtilisateurConverter.toUtilisateurEntity(utilisateurDTO));
            return UtilisateurConverter.toUtilisateurDTO(user);
        }
        return null;
    }


    @Transactional
    public UtilisateurDTO mjUtilisateur(UtilisateurDTO utilisateurDTO) {
        return miseaJourUtilisateur(utilisateurDTO);
    }

    /**
     * pour active ou desactive un utilisateur
     *
     * @param utilisateurDTO
     * @return
     */
    @Transactional
    public UtilisateurDTO activeOrDesactiveUtilisateur(UtilisateurDTO utilisateurDTO) {
        UtilisateurEntity userDetail = utilisateurRepository.findById(utilisateurDTO.getId()).orElse(null);
        if (userDetail != null) {
            userDetail.setActive(utilisateurDTO.isActive());
            userDetail = utilisateurRepository.save(userDetail);
        }
        return UtilisateurConverter.toUtilisateurDTO(userDetail);
    }

    @Transactional
    public List<UtilisateurDTO> getAllUtilisateurs() {
        List<UtilisateurEntity> users = utilisateurRepository.findAll();
        List<UtilisateurDTO> utilisateurDTOList = users.stream()
                .map(u -> UtilisateurConverter.toUtilisateurDTO(u))
                .collect(Collectors.toList());

        return utilisateurDTOList;
    }

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
        return UtilisateurConverter.toUtilisateurDTO(userDetail);
    }

    @Transactional
    public UtilisateurDTO chargerUtilisateur(String email) {
        UtilisateurEntity userDetail = utilisateurRepository.findUtilisateurEntitiesByLoginExists(email);
        return UtilisateurConverter.toUtilisateurDTO(userDetail);
    }

    private boolean loginExiste(String login) {
        Integer val = this.utilisateurRepository.checkUtilisateurEntitiesByLoginExists(login);
        if (val > 0) {
            return true;
        }
        return false;
    }

    @Transactional
    public UtilisateurDTO chargerUtilisateurParId(Long id) {
        Optional<UtilisateurEntity> user = utilisateurRepository.findById(id);
        if (user.isPresent()) {
            return UtilisateurConverter.toUtilisateurDTO(user.get());
        } else {
            throw new CommonMetierException(HttpStatus.NOT_ACCEPTABLE.value(), ErreurEnum.ERR_CONTRAT_NOT_FOUND);
        }
    }
    @Transactional
    public boolean checkEmail(Long id,String email) {
       Integer nb= utilisateurRepository.checkEmailExists(email,id);
        return nb>0?true:false;
    }
    @Transactional
    public boolean checkEmail(String email) {
        Integer nb= utilisateurRepository.checkEmailExists(email);
        return nb>0?true:false;
    }
}
