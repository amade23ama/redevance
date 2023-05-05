import {Component, OnInit} from '@angular/core';
import {HomeCard} from "../../../core/interfaces/infotuiles/homeCard";
import {AppConfigService} from "../../../core/services/app-config.service";
import {Router} from "@angular/router";
import {AuthService} from "../../../core/services/auth.service";

@Component({
  selector: 'app-home-card',
  templateUrl: './home-card.component.html',
  styleUrls: ['./home-card.component.scss']
})
export class HomeCardComponent implements  OnInit {
  /** Tuiles. */
  public infoTuiles: HomeCard[];
  constructor(public appConfig:AppConfigService,public router: Router,public auth:AuthService) {
  }
  ngOnInit(): void {
  }

}
