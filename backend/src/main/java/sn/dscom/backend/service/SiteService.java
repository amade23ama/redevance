package sn.dscom.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import sn.dscom.backend.common.dto.SiteDTO;
import sn.dscom.backend.common.dto.VehiculeDTO;
import sn.dscom.backend.database.entite.DepotEntity;
import sn.dscom.backend.database.entite.SiteEntity;
import sn.dscom.backend.database.repository.SiteRepository;
import sn.dscom.backend.service.converter.SiteConverter;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class SiteService {
    private SiteRepository siteRepository;

    @Transactional
    public SiteDTO enregistrerSite(SiteDTO siteDTO) {
        //todo
        return miseAjourSite(siteDTO);
    }

    private SiteDTO miseAjourSite(SiteDTO siteDTO) {
        //todo
        SiteEntity siteEntity =SiteConverter.toSiteEntity(siteDTO);
        final Optional<SiteEntity> entitySite = siteRepository.findById(siteDTO.getId());
        if(entitySite.isPresent()){
            siteEntity.setDateModification(new Date());
        }else {
            siteEntity.setDateCreation(new Date());
        }
        SiteEntity site =siteRepository.save(siteEntity);
        return  SiteConverter.toSiteDTO(site);
    }

}
