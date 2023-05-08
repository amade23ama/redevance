package sn.dscom.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.dto.ProfilDTO;
import sn.dscom.backend.database.entite.ProfilEntity;
import sn.dscom.backend.database.repository.ProfilRepository;
import sn.dscom.backend.database.repository.UtilisateurRepository;
import sn.dscom.backend.service.converter.ProfilConverter;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ProfilService {
    private ProfilRepository profilRepository;
    @Transactional
    public List<ProfilDTO> chargementProfils(){
        List<ProfilEntity> profilEntitys =profilRepository.findAll();
        List<ProfilDTO> items=profilEntitys.stream()
                .map(profil-> ProfilConverter.toProfilDTO(profil))
                .collect(Collectors.toList());
        return items;
    }
}
