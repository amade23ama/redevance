import {Utilisateur} from "./utilisateur";
import {DatePipe} from "@angular/common";
import {BuilderDtoJsonAbstract, NoParamConstructor} from "./BuilderDtoJsonAbstract";
import {Profil} from "./profil";
import {Site} from "./site";

export class Depot extends BuilderDtoJsonAbstract{
  /** id */
  id:number;

  /** nom */
  nom:string;
  statut:string;

  /** date Heure Depot */
  dateHeureDepot:Date

  /** date Heure Fin Depot */
  dateHeureFinDepot:Date;

  /** nom Fichier */
  nomFichier:string;

  /** deposeur */
  deposeur:Utilisateur;

  /** nb Chargement Deposes */
  nbChargementDeposes:number;

  /** nb Chargement ReDeposes */
  nbChargementReDeposes:number;

  /** nb Chargement Erreur */
  nbChargementErreur :number;
  site:Site;
  nbChargementTotal:number;
  static fromJson<T>(json: any, ctor?: NoParamConstructor<T>): T {
    const depot: any = BuilderDtoJsonAbstract.fromJson(json, Depot);
    if (json.dateHeureDepot) {
      depot.dateHeureDepot= (new DatePipe(BuilderDtoJsonAbstract.JSON_DATE_PIPE_LOCALE))
        .transform(json.dateHeureDepot, BuilderDtoJsonAbstract.DATE_FORMAT_SIMPLEJSON)
    }
    if (json.site) {
      depot.site= BuilderDtoJsonAbstract.fromJson(json.site, Site);
    }
    if (json.dateHeureFinDepot) {
      depot.dateHeureFinDepot= (new DatePipe(BuilderDtoJsonAbstract.JSON_DATE_PIPE_LOCALE))
        .transform(json.dateHeureFinDepot, BuilderDtoJsonAbstract.DATE_FORMAT_SIMPLEJSON)
    }
    return depot;
  }
}
