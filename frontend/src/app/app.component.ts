import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { Globals } from "./app.constants";
import { AuthService } from "./core/services/auth.service";
import { UrlService } from './core/services/url.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent  implements OnInit {
  isLoggedIn = false;
  constructor(public authService :AuthService,public globals: Globals,public router:Router, public urlService: UrlService) {
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
