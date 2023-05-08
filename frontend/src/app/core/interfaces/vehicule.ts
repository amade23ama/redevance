import {BuilderDtoJsonAbstract} from "./BuilderDtoJsonAbstract";
import {Transporteur} from "./transporteur";

export class Vehicule  extends BuilderDtoJsonAbstract{
  id:number;
  immatriculation:string;
  transporteur:Transporteur;
  nom:string;
  volume:number;
  dateCreation:Date ;
  dateModification:Date ;
}
