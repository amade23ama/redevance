package sn.dscom.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.dscom.backend.common.dto.ProduitDTO;
import sn.dscom.backend.common.dto.SiteDTO;
import sn.dscom.backend.common.dto.VehiculeDTO;
@RestController
@RequestMapping("/api")
public class ProduitController {
    @Autowired
    ProduitController(){}
    public ProduitDTO enregistrerProduit(@RequestBody ProduitDTO produitDTO) {
        //todo
        return  null;
    }
    public ProduitDTO modifierProduit(@RequestBody ProduitDTO produitDTO) {
        //todo
        return  null;
    }
    public ProduitDTO supprimerProduit(@RequestBody ProduitDTO produitDTO) {
        //todo
        return  null;
    }
}
