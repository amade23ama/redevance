package sn.dscom.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.dscom.backend.common.dto.ProfilDTO;
import sn.dscom.backend.service.ProfilService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ConfigController {
    private ProfilService profilService;
    @Autowired
    ConfigController(ProfilService profilService){
        this.profilService=profilService;
    }
    @GetMapping("/profils")
    public List<ProfilDTO> chargementProfils(){
        return this.profilService.chargementProfils();
    }
}
