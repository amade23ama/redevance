import {Component, OnInit} from '@angular/core';
import {Globals} from "./app.constants";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent  implements OnInit {
  title = 'frontend';
  constructor(public globals: Globals) {
    // Affichage d`un spinner lors du chargement
    globals.loading = false;
  }
  ngOnInit(): void {
    console.log(" bonjour")
  }
}
