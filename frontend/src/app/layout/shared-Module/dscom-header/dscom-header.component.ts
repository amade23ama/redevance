import {Component, OnInit} from '@angular/core';
import {AppConfigService} from "../../../core/services/app-config.service";
import {AuthService} from "../../../core/services/auth.service";
import {Router} from "@angular/router";
import {UtilisateurService} from "../../../core/services/utilisateur.service";

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

  }
  /*logout(){
    this.authService.logout().subscribe(()=>{
      console.log(" deconnexion 1")
      this.router.navigate(["/deconnexion"]);
    })
  }
  */

  logout(){
    this.authService.logoutTest()
      this.router.navigate(["/login"]);
  }
}
