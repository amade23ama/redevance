import {Component, OnInit} from '@angular/core';
import {HomeCard} from "../../../core/interfaces/infotuiles/homeCard";
import {AppConfigService} from "../../../core/services/app-config.service";
import {Router} from "@angular/router";
import {AuthService} from "../../../core/services/auth.service";
import {TypeInfoTuile} from "../../../core/enum/TypeInfoTuile";
import {TuileService} from "../../../core/services/tuile.service";

@Component({
  selector: 'app-home-card',
  templateUrl: './home-card.component.html',
  styleUrls: ['./home-card.component.scss']
})
export class HomeCardComponent implements  OnInit {
  /** Tuiles. */
  //public infoTuiles: HomeCard[];
  infoTuiles$=this.tuileService.infoTuiles$
  constructor(public appConfig:AppConfigService,public router: Router,public auth:AuthService,
              public tuileService:TuileService) {
  }
  ngOnInit(): void {
    this.tuileService.getInfosTuiles().subscribe()
  }
  public getTitreTuile(typeHomecard: string):string{
    switch (typeHomecard) {
      case TypeInfoTuile.CHARGEMENT:
        return 'card.chargement.title';
      case TypeInfoTuile.SITE:
        return 'card.site.title';
      case TypeInfoTuile.DEPOT:
        return 'card.depot.title';
      case TypeInfoTuile.TRANSPORTEUR:
        return 'card.transporteur.title';
    }
    return '';
  }
  /**
   * Méthode permettant de définir la description de la homecard.
   * @param typeHomecard le type du homecard.
   */
  public getDescriptionTuile(typeHomecard: string):string{
    switch (typeHomecard) {
      case TypeInfoTuile.CHARGEMENT:
        return 'card.chargement.description';
      case TypeInfoTuile.SITE:
        return 'card.site.description';
      case TypeInfoTuile.DEPOT:
        return 'card.depot.description';
      case TypeInfoTuile.TRANSPORTEUR:
        return 'card.transporteur.description';
    }
    return '';
  }

  /**
   * Méthode permettant de définir la classe du bouton de la homecard.
   * @param typeHomecard le type du homecard.
   */
  public getBoutonClasseTuile(typeHomecard: string):string{
    switch (typeHomecard) {
      case TypeInfoTuile.CHARGEMENT:
        return 'instruct--round';
      case TypeInfoTuile.SITE:
        return 'site--round';
      case TypeInfoTuile.DEPOT:
        return 'ongoing--round';
      case TypeInfoTuile.TRANSPORTEUR:
        return 'stamped--round';
    }
    return '';
  }
  /**
   * Méthode permettant de définir la classe du titre de la homecard.
   * @param typeHomecard le type de la homecard.
   */
  public getClasseTitre(typeHomecard: string): string {
    switch (typeHomecard) {
      case TypeInfoTuile.CHARGEMENT:
        return 'instruct--tile';
      case TypeInfoTuile.DEPOT:
        return 'ongoing--tile';
      case TypeInfoTuile.SITE:
        return 'site--tile';
      case TypeInfoTuile.TRANSPORTEUR:
        return 'stamped--tile';
    }
    return '';
  }
  /**
   * Méthode permettant d'obtenir l'année en cours
   */
  public getCurrentYear(): any {
    return new Date().getFullYear();
  }
  /**
   * Renvoie l'annee courante.
   */
  anneeCourante(): any {
    return new Date().getFullYear();
  }
  chargerdepot(val:any,valeur:any){

  }
}
