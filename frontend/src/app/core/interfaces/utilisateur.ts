import {BuilderDtoJsonAbstract} from "./BuilderDtoJsonAbstract";

export class Utilisateur extends BuilderDtoJsonAbstract{

  id: number;
  nom: string;
  prenom: string;
  login: string;
  email:string;

  /**
   * Méthode permettant de retourner le nom prénom d'un utilisateur.
   */
  get label(): string {
    console.log(" erreor")
    let nomPrenom = '';
    if (this.nom) {
      nomPrenom=nomPrenom.concat("",this.nom);
    }
    if (this.prenom) {
      nomPrenom=nomPrenom.concat(" ", this.prenom);
    }
    return nomPrenom;
  }
}
