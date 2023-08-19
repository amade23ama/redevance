import { Transporteur } from "./transporteur";
import {Categorie} from "./categorie";
import {BuilderDtoJsonAbstract, NoParamConstructor} from "./BuilderDtoJsonAbstract";
import {Profil} from "./profil";

export class Vehicule extends BuilderDtoJsonAbstract{
  id:number;
  immatriculation:string;
  transporteur:Transporteur;
  categorie:Categorie
  dateCreation:Date ;
  dateModification:Date ;

  static fromJson<T>(json: any, ctor?: NoParamConstructor<T>): T {
    const vehicule: any = BuilderDtoJsonAbstract.fromJson(json, Vehicule);
    if (json.transporteur) {
      vehicule.transporteur = Profil.fromJson(json.transporteur, Transporteur);
    }
    if (json.categorie) {
      vehicule.categorie = Profil.fromJson(json.categorie, Categorie);
    }
    return vehicule;
  }
}
