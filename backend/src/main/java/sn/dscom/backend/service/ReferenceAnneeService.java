package sn.dscom.backend.service;

import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.dto.ReferenceAnneeDTO;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.ReferenceAnneeEntity;
import sn.dscom.backend.database.repository.ReferenceAnneeRepository;
import sn.dscom.backend.service.converter.ReferenceAnneeConverter;
import sn.dscom.backend.service.interfaces.IReferenceAnneeService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Transactional
public class ReferenceAnneeService implements IReferenceAnneeService {
    static Logger logger = LoggerFactory.getLogger(ReferenceAnneeService.class);
    private ReferenceAnneeRepository referenceAnneeRepository;
    private final Transformer<ReferenceAnneeDTO, ReferenceAnneeEntity> referenceAnneeConverter = new ReferenceAnneeConverter();
    @Builder
    public ReferenceAnneeService(ReferenceAnneeRepository referenceAnneeRepository) {
        this.referenceAnneeRepository = referenceAnneeRepository;
    }
    @Transactional
    @Override
    public List<ReferenceAnneeDTO> getAllAnnee() {
       List<ReferenceAnneeEntity> listAnnees=referenceAnneeRepository.findAll();
        return listAnnees.stream()
                .map(this.referenceAnneeConverter::reverse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReferenceAnneeDTO> saveAll(List<ReferenceAnneeDTO> referenceAnneeDTOS) {
        List<ReferenceAnneeEntity> listAnnees=referenceAnneeDTOS.stream().map(this.referenceAnneeConverter::transform)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<ReferenceAnneeEntity> listAnneesDto= referenceAnneeRepository.saveAll(listAnnees);
        return listAnneesDto.stream()
                .map(this.referenceAnneeConverter::reverse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
