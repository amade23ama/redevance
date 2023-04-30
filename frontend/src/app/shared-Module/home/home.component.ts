import {Component, OnInit} from '@angular/core';
import {Globals} from "../../app.constants";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent  implements OnInit{
  constructor() {
    // Affichage d`un spinner lors du chargement
  }
  ngOnInit(): void {

  }

}
