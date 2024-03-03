package sn.dscom.backend.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.dto.ErreurDepotDTO;
import sn.dscom.backend.database.repository.ErreurDepotRepository;
import sn.dscom.backend.database.repository.ExploitationRepository;
import sn.dscom.backend.service.interfaces.IErreurDepotService;

import java.util.List;
import java.util.Optional;
@Slf4j
@Transactional
public class ErreurDepotService implements IErreurDepotService {
    private final ErreurDepotRepository erreurDepotRepository;
    @Override
    public Optional<ErreurDepotDTO> enregistrerErreurDepot(ErreurDepotDTO erreurDepotDTO) {
        return Optional.empty();
    }

    @Override
    public Optional<List<ErreurDepotDTO>> rechercherErreurDepotByCriteres(ErreurDepotDTO erreurDepotDTO) {
        return Optional.empty();
    }
    @Builder
    public ErreurDepotService(ErreurDepotRepository erreurDepotRepository) {
        this.erreurDepotRepository = erreurDepotRepository;
    }
}
