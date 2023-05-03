import {Component, OnInit} from '@angular/core';
import {AuthService} from "../core/services/auth.service";
import {Globals} from "../app.constants";
import {Router} from "@angular/router";

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

  constructor(public authService: AuthService,public globals: Globals,
              public router:Router) {
    // Affichage d`un spinner lors du chargement
    globals.loading = false;
    this.isLoggedIn = this.authService.isLoggedIn()

  }

  ngOnInit(): void {

    }
  onSidenavClose(){

  }
  logout(){
    this.authService.logoutTest()
    this.router.navigate(["/login"]);
    //this.authService.logout()
  }
}
