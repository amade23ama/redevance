package sn.dscom.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import sn.dscom.backend.common.dto.ProduitDTO;
import sn.dscom.backend.common.dto.SiteDTO;
import sn.dscom.backend.common.dto.VehiculeDTO;

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
