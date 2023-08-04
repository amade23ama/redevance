import {Component, OnInit} from '@angular/core';
import {Globals} from "./app.constants";
import {AuthService} from "./core/services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent  implements OnInit {
  isLoggedIn = false;
  constructor(public authService :AuthService,public globals: Globals,public router:Router) {
    // Affichage d`un spinner lors du chargement
    globals.loading = false;
    this.authService.isLoggedIn()
  }
  ngOnInit(): void {
  this.isLoggedIn=this.authService.isLoggedIn()
    if(!this.isLoggedIn){
      this.router.navigate(["/login"])
    }
    console.log(" AppComponent ")
  }
}
