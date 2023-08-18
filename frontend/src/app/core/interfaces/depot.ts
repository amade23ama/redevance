import {Utilisateur} from "./utilisateur";

export class Depot{
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

}
