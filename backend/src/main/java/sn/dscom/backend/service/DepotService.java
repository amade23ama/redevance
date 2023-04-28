package sn.dscom.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.constants.Enum.ErreurEnum;
import sn.dscom.backend.common.dto.DepotDTO;
import sn.dscom.backend.common.exception.CommonMetierException;
import sn.dscom.backend.database.entite.DepotEntity;
import sn.dscom.backend.database.repository.DepotRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class DepotService {
    private final DepotRepository depotRepository;


    public DepotDTO getDepotParId(final @NotNull Long id) {
        //todo
        return null;
    }
    @Transactional
    public DepotDTO sauvegarderDepot(final DepotDTO depotDTO) {
        // todo
        return null;
    }

    private DepotEntity chargerDepot(final Long idDepot) {
        //todo
      return null;
    }

    @Transactional
    public ResponseEntity<Void> supprimerDepot(final @NotNull Long id) {
        // todo
        final Optional<DepotEntity> depotEntity = depotRepository.findById(id);
        if (!depotEntity.isPresent()) {
            throw new CommonMetierException(HttpStatus.NOT_ACCEPTABLE.value(), ErreurEnum.ERR_CONTRAT_NOT_FOUND);

        }
        depotRepository.delete(depotEntity.get());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    public List<DepotDTO> getAllDepot(){
        // todo
        return null;
    }
    // todo
    public List<DepotDTO> getDepotParCritereRecherche(){
        //  todo
        return null;
    }
    public List<DepotDTO> getDepotParDeposeur() {
        //todo
        return null;
    }
}
