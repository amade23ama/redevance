import { Transporteur } from "./transporteur";
import {Categorie} from "./categorie";

export class Vehicule /* extends BuilderDtoJsonAbstract*/{
  id:number;
  immatriculation:string;
  transporteur:Transporteur;
  categorie:Categorie
  nom:string;
  volume:number;
  dateCreation:Date ;
  dateModification:Date ;
}
