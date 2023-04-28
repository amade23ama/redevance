package sn.dscom.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.dto.ConfigDTO;
import sn.dscom.backend.common.dto.TypeProfilDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ConfigService {

    public ConfigDTO chargerConfiguration() {
        final ConfigDTO configDTO = new ConfigDTO();
        configDTO.getTypeProfilDTOList().addAll(chargerListTypeProfils());
        return configDTO;
    }

    private List<TypeProfilDTO> chargerListTypeProfils() {
        // todo
        return null;

    }
}

