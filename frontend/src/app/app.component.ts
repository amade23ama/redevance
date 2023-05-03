import {Component, OnInit} from '@angular/core';
import {Globals} from "./app.constants";
import {AuthService} from "./core/services/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent  implements OnInit {
  title = 'frontend';
  isLoggedIn = false;
  constructor(public authService :AuthService,public globals: Globals) {
    // Affichage d`un spinner lors du chargement
    globals.loading = false;
    this.authService.isLoggedIn()
  }
  ngOnInit(): void {
  this.isLoggedIn=this.authService.isLoggedIn()
    console.log(" AppComponent ")
  }
}
