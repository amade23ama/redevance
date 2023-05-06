package sn.dscom.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.database.repository.ChargementRepository;

import java.time.LocalDateTime;

@Service
@Transactional
@AllArgsConstructor
public class ChargementService {
    private ChargementRepository chargementRepository;
    public Integer compterChargement(final LocalDateTime dateMiseEnService) {
        // todo
        return chargementRepository.compterChargementPardate(dateMiseEnService);

    }
}
