package sn.dscom.backend.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.dto.DepotDTO;
import sn.dscom.backend.common.dto.ErreurDepotDTO;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.DepotEntity;
import sn.dscom.backend.database.entite.ErreurDepotEntity;
import sn.dscom.backend.database.entite.ReferenceAnneeEntity;
import sn.dscom.backend.database.repository.ErreurDepotRepository;
import sn.dscom.backend.database.repository.ExploitationRepository;
import sn.dscom.backend.service.converter.DepotConverter;
import sn.dscom.backend.service.converter.ErreurDepotConverter;
import sn.dscom.backend.service.interfaces.IErreurDepotService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Transactional
public class ErreurDepotService implements IErreurDepotService {
    private final ErreurDepotRepository erreurDepotRepository;
    private final Transformer<ErreurDepotDTO, ErreurDepotEntity> erreurDepotConverteur = new ErreurDepotConverter();
    @Override
    public Optional<ErreurDepotDTO> enregistrerErreurDepot(ErreurDepotDTO erreurDepotDTO) {
        return Optional.of(this.erreurDepotConverteur.reverse(this.erreurDepotRepository.save(this.erreurDepotConverteur.transform(erreurDepotDTO))));
    }

    @Override
    public Optional<List<ErreurDepotDTO>> rechercherErreurDepotByCriteres(ErreurDepotDTO erreurDepotDTO) {
        return Optional.empty();
    }

    @Override
    public Optional<List<ErreurDepotDTO>> enregistrerAllErreurDepot(List<ErreurDepotDTO> erreurDepotDTOList) {
        List<ErreurDepotEntity> listErreurDepotEntity=erreurDepotDTOList.stream().map(this.erreurDepotConverteur::transform)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return Optional.of((this.erreurDepotRepository.saveAll(listErreurDepotEntity))
                .stream().map(this.erreurDepotConverteur::reverse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }

    @Builder
    public ErreurDepotService(ErreurDepotRepository erreurDepotRepository) {
        this.erreurDepotRepository = erreurDepotRepository;
    }
}
