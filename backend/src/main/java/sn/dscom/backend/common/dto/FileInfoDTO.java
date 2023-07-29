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

    /** data */
    private  byte[] data;

    /** nomsColonnesFile */
    private String[] colonnesFile;

    /** taille */
    private  Long taille;

    /** donneesFichier */
    private List<String[]> donneesFichier;

    /** colonnes des tables */
    private String[] colonneTable;

    private List<String> enteteFile;
    private Map<String,String> headerFileToDatabase ;
}
