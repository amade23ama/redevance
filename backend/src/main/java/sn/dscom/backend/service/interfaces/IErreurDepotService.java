package sn.dscom.backend.service.interfaces;

import sn.dscom.backend.common.dto.DepotDTO;
import sn.dscom.backend.common.dto.ErreurDepotDTO;
import sn.dscom.backend.common.dto.ExploitationDTO;

import java.util.List;
import java.util.Optional;

public interface IErreurDepotService {
    Optional<ErreurDepotDTO> enregistrerErreurDepot(ErreurDepotDTO erreurDepotDTO);
    Optional<List<ErreurDepotDTO>> rechercherErreurDepotByCriteres(ErreurDepotDTO erreurDepotDTO);
}
