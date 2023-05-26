import {Component, OnInit} from '@angular/core';
import {AuthService} from "../core/services/auth.service";
import {Globals} from "../app.constants";
import {ActivatedRoute, Router} from "@angular/router";
import {SessionTimerService} from "../core/services/Session.timer.service";

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent implements OnInit {
  /**
   *  Boolean pour savoir si l`utilisateur est connecte
   *
   * @memberof HomeComponent
   */
  isLoggedIn = false;
  tempsRestant: number;
  constructor(public authService: AuthService,public globals: Globals,
              public router:Router,private readonly activatedRoute: ActivatedRoute,
              private sessionTimerService: SessionTimerService) {
    // Affichage d`un spinner lors du chargement
    globals.loading = false;
    this.isLoggedIn = this.authService.isLoggedIn()

  }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(async params => {
      console.error(" message Layout Home:")
      /*if (params.errorMsg) {
        this.messageService.openSnackBar(params.errorMsg, '', 'error');
        // clear errorMessage
        this.router.navigate([]);
      }
      */
    });
    /*this.sessionTimerService.startTimer();
    this.actualiserTempsRestant()
    this.sessionTimerService.getSessionExpiree().subscribe(() => {
      // Gérez l'expiration de session ici (ex : déconnexion, redirection, affichage d'une alerte)
      console.log('Session expirée');

    });
    */


    }
  onSidenavClose(){

  }
  logout(){

    this.authService.logout().subscribe(()=>{
      this.router.navigate(["/login"]);
    })
  }

  actualiserTempsRestant() {
    this.tempsRestant = this.sessionTimerService.getTempsRestant();
  }
}
