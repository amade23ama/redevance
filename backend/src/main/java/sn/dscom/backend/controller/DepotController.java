package sn.dscom.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.dscom.backend.common.dto.DepotDTO;
import sn.dscom.backend.common.dto.ExploitationDTO;
@RestController
@RequestMapping("/api")
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
