import {Component, OnInit} from '@angular/core';
import {Globals} from "../../../app.constants";
import {AuthService} from "../../../core/services/auth.service";
import {UtilisateurService} from "../../../core/services/utilisateur.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent  implements OnInit{
  /**
   *  Boolean pour savoir si l`utilisateur est connecte
   *
   * @memberof HomeComponent
   */
  isLoggedIn = false;

  constructor(public authService :AuthService,private utilisateurService:UtilisateurService) {
    // Affichage d`un spinner lors du chargement
    this.isLoggedIn=this.authService.isLoggedIn()

  }
  ngOnInit(): void {
    console.log(" HomeComponent 1")
   this.isLoggedIn=this.authService.isLoggedIn()
    this.utilisateurService.getUtilisateurConnecte().subscribe()
  }

}
