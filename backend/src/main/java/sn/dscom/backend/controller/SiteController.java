package sn.dscom.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.dscom.backend.common.dto.SiteDTO;
import sn.dscom.backend.common.dto.VehiculeDTO;
@RestController
@RequestMapping("/api")
public class SiteController {
    @Autowired
    SiteController(){

    }

    public SiteDTO enregistrerSite(@RequestBody SiteDTO siteDTO) {
        //todo
        return  null;
    }
    public SiteDTO modifierSite(@RequestBody SiteDTO siteDTO) {
        //todo
        return  null;
    }
    public SiteDTO supprimerSite(@RequestBody VehiculeDTO siteDTO) {
        //todo
        return  null;
    }
}
