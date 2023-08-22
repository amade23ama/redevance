import {Component, OnInit} from "@angular/core";
import {AppConfigService} from "../../../core/services/app-config.service";

@Component({
  selector: 'app-recherche-chargement-depot',
  templateUrl: './recherche-chargement-depot.component.html',
  styleUrls: ['./recherche-chargement-depot.component.scss']
})
export class RechercheChargementDepotComponent implements OnInit{
  constructor(public appConfig:AppConfigService) {
  }
  ngOnInit(): void {
  }

}
