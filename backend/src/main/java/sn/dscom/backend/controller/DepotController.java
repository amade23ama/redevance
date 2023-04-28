package sn.dscom.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import sn.dscom.backend.common.dto.DepotDTO;
import sn.dscom.backend.common.dto.ExploitationDTO;

public class DepotController {
    @Autowired
    DepotController(){}

    public DepotDTO enregistrerDepot(@RequestBody DepotDTO depotDTO) {
        //todo
        return  null;
    }
    public DepotDTO modifierDepot(@RequestBody DepotDTO depotDTO) {
        //todo
        return  null;
    }
    public DepotDTO supprimerDepot(@RequestBody DepotDTO depotDTO) {
        //todo
        return  null;
    }
}
