package sn.dscom.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.dscom.backend.common.dto.HomeCardDTO;
import sn.dscom.backend.service.InformationTuilesService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InfoTuilesController {
    private InformationTuilesService infoTuileService;
    @Autowired
    InfoTuilesController(InformationTuilesService infoTuileService){
    this.infoTuileService=infoTuileService;
    }
    @GetMapping("/chargementInfoTuiles")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public List<HomeCardDTO> chargementInfoTuiles() {
        return infoTuileService.getInfoTuiles();
    }
}
