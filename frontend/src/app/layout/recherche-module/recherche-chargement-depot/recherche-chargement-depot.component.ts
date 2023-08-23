import {Component, OnInit} from "@angular/core";
import {AppConfigService} from "../../../core/services/app-config.service";
import {DepotService} from "../../../core/services/depot.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-recherche-chargement-depot',
  templateUrl: './recherche-chargement-depot.component.html',
  styleUrls: ['./recherche-chargement-depot.component.scss']
})
export class RechercheChargementDepotComponent implements OnInit{
  depot$=this.depotService.depot$
  constructor(public appConfig:AppConfigService,public depotService:DepotService,
              private readonly activatedRoute: ActivatedRoute) {
  }
  ngOnInit(): void {
    this.activatedRoute.queryParams?.subscribe(async params => {
      if (params['contextInfo']) {
        this.depotService.getDepotParId(params['contextInfo']).subscribe();
      }
    });
  }

}
