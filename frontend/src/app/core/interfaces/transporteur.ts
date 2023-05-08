import {BuilderDtoJsonAbstract} from "./BuilderDtoJsonAbstract";
import {Vehicule} from "./vehicule";

export class Transporteur extends BuilderDtoJsonAbstract{
  id:number;
  type:string;
  nom:string;
  prenom:string;
  telephone:string;
  email:string;
  adresse:string;
  dateCreation:Date ;
  dateModification:Date;
  vehiculeListes:Vehicule[] ;
}
