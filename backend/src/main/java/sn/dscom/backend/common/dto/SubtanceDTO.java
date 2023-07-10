package sn.dscom.backend.common.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * la subtance du chargement
 * @author diome
 */
@Data
@Builder
public class SubtanceDTO {
    /** la densite en Gramme par Centimètre */
    public int densiteGrammCm;

    /** la densite en kilogramme par mètre */
    public int densiteKgM;

    /** la date Creation */
    public Date dateCreation;

    /** date Modif */
    public Date dateModif;
}
