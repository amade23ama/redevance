package sn.dscom.backend.common.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfigDTO {

    private String urlLienDTO;
    private final List<TypeProfilDTO> typeProfilDTOList = new ArrayList<>();
    //todo
}
