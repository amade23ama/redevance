package sn.dscom.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import sn.dscom.backend.common.dto.UtilisateurDTO;
import sn.dscom.backend.common.dto.VehiculeDTO;

public class VehiculeController {
    @Autowired
    VehiculeController(){}
    public VehiculeDTO enregistrerVehicule(@RequestBody VehiculeDTO vehiculeDTO) {
        //return utilisateurService.sauvegarderUtilisateur(utilisateurDTO);
        //todo
        return  null;
    }
    public VehiculeDTO modifierVehicule(@RequestBody VehiculeDTO vehiculeDTO) {
        //return utilisateurService.sauvegarderUtilisateur(utilisateurDTO);
        //todo
        return  null;
    }
    public VehiculeDTO supprimerVehicule(@RequestBody VehiculeDTO vehiculeDTO) {
        //return utilisateurService.sauvegarderUtilisateur(utilisateurDTO);
        //todo
        return  null;
    }
}
