package sn.dscom.backend.service.interfaces;

import sn.dscom.backend.common.dto.ReferenceAnneeDTO;

import java.util.List;

public interface IReferenceAnneeService {

    List<ReferenceAnneeDTO> getAllAnnee();
    List<ReferenceAnneeDTO> saveAll(List<ReferenceAnneeDTO> referenceAnneeDTOS);

}
