package sn.dscom.backend.batchs;

import sn.dscom.backend.common.dto.ChargementDTO;
import sn.dscom.backend.common.dto.ErreurDepotDTO;

import java.util.List;

public class ImportProcessingDTO {
    private List<ChargementDTO> processedItems;
    private List<ErreurDepotDTO> errorItems;

    public ImportProcessingDTO(List<ChargementDTO> processedItems, List<ErreurDepotDTO> errorItems) {
        this.processedItems = processedItems;
        this.errorItems = errorItems;
    }

    public List<ChargementDTO> getProcessedItems() {
        return processedItems;
    }

    public List<ErreurDepotDTO> getErrorItems() {
        return errorItems;
    }
}
