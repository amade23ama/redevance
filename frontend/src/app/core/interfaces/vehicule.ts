import { BuilderDtoJsonAbstract, NoParamConstructor } from "./BuilderDtoJsonAbstract";
import { Categorie } from "./categorie";
import { Profil } from "./profil";
import { Transporteur } from "./transporteur";
/**
 * Vehicule model de donnée
 */
export class Vehicule extends BuilderDtoJsonAbstract{
  /** identifiant technique */
  id:number;

  /** immatriculation du véhicule */
  immatriculation:string;

  /** le transporteur transporteur */
  transporteur:Transporteur;

  /** categorie du véhicule */
  categorie:Categorie;

  /** date Creation */
  dateCreation: Date ;

  /** date Modification */
  dateModification: Date ;

  /** poids Vide */
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
