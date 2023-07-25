package sn.dscom.backend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DataStrategyDTO
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataStrategyDTO {

    /** site */
    private String site;

    /** date */
    private String date;
}
