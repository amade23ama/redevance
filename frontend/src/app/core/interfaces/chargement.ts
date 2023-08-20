import {BuilderDtoJsonAbstract} from "./BuilderDtoJsonAbstract";
import {Depot} from "./depot";
import {Site} from "./site";
import {Vehicule} from "./vehicule";
import {Exploitation} from "./exploitation";
import {Produit} from "./produit";
import {Plateforme} from "./plateforme";
import {Subtance} from "./subtance";

export class Chargement extends BuilderDtoJsonAbstract{
  /** l'id */
   id:number;

  /** la destination du chargement*/
  destination:string;

  /** la date du chargement*/
  datePesage:Date;

  /** le poid du chargement*/
  poids:number;

  /** le poid max du chargement*/
  poidsMax:number;

  /** le poid Subst du chargement*/
  poidsSubst:number;

  /** le volume du chargement*/
  volumeSubst:number;

  /** le volume moyeb du chargement*/
  volumeMoyen:number;

  /** ecart */
  ecart:number;

  /** la date Creation*/
  dateCreation:Date;

  /** date Modif*/
  dateModif:Date;

  /** date Modif*/
  subtance:Subtance ;
  /** la plateforme*/
  plateforme:Plateforme ;
  /** le depot*/
  depot:Depot;
  /** le site*/
  site:Site;
  /** le site*/
  vehicule:Vehicule;
  /** le site d'exploitation */
   exploitation:Exploitation;
  /**  d'produit */
  produit:Produit;
}
