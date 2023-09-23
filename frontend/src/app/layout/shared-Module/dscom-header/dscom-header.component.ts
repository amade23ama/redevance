import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { DroitEnum } from 'src/app/core/enum/droit-enum';
import { Utilisateur } from 'src/app/core/interfaces/utilisateur';
import { AppConfigService } from "../../../core/services/app-config.service";
import { AuthService } from "../../../core/services/auth.service";
import { UtilisateurService } from "../../../core/services/utilisateur.service";

@Component({
  selector: 'app-dscom-header',
  templateUrl: './dscom-header.component.html',
  styleUrls: ['./dscom-header.component.scss']
})
export class DscomHeaderComponent implements OnInit  {
  isLoggedIn = false;
  utilisateurConnecte$=this.utilisateurService.getUtilisateurConnecte()
  constructor(public appConfig:AppConfigService,public authService:AuthService,
              private router:Router , public utilisateurService:UtilisateurService) {

  }
  ngOnInit(): void {
    this.isLoggedIn=this.authService.isLoggedIn()
    this.utilisateurService.updateConnected().subscribe()

  }logout(){
    this.authService.logout().subscribe(()=>{
      this.router.navigate(["/login"]);
    })
  }

  /**
   * modification du mot de passe
   * @param utilisateurConnecte 
   */
  changerPW(utilisateurConnecte: Utilisateur) {
    this.router.navigate(["admin/user/update"], {queryParams: {'contextInfo':utilisateurConnecte.id }});
  }

  /**
   * Retour le profil de l'utilisateur connecté
   * @returns 
   */
  getProfil() {
    let role = this.authService.droits[0];
    
    switch (role) {
      case DroitEnum.ADMIN: return 'Administrateur';
      case DroitEnum.CONSULT: return 'Consultation';
      case DroitEnum.EDIT: return 'Editeur';
      default: return '';
    }
    
  }


}
