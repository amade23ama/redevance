package sn.dscom.backend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * FileInfoDTO
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfoDTO implements Serializable {

    /** taille */
    private  Long taille;

    /** donneesFichier */
    private List<String[]> donneesFichier;

    /** colonnes des tables */
    private List<String> colonneTable;

    /** entete File */
    private List<String> enteteFile;
}
