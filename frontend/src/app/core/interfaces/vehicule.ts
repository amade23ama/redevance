import { BuilderDtoJsonAbstract, NoParamConstructor } from "./BuilderDtoJsonAbstract";
import { Categorie } from "./categorie";
import { Profil } from "./profil";
import { Transporteur } from "./transporteur";

export class Vehicule extends BuilderDtoJsonAbstract{
  id:number;
  immatriculation:string;
  transporteur:Transporteur;
  categorie:Categorie
  dateCreation:Date ;
  dateModification:Date ;
  poidsVide: number;

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
