package sn.dscom.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.database.repository.TransporteurRepository;

import java.time.LocalDateTime;

@Service
@Transactional
@AllArgsConstructor
public class TransporteurService {
    private TransporteurRepository transporteurRepository;
    @Transactional
    public Integer compterTransporteur(final LocalDateTime dateMiseEnService) {
        // todo
        return transporteurRepository.compterTransporteurPardate(dateMiseEnService);

    }
}
