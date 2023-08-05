import {BuilderDtoJsonAbstract, NoParamConstructor} from "./BuilderDtoJsonAbstract";
import {Profil} from "./profil";
import {DroitEnum} from "../enum/droit-enum";

export class Utilisateur extends BuilderDtoJsonAbstract{

  id: number;
  nom: string;
  prenom: string;
  login: string;
  email:string;
  password:string;
  telephone:string;
  active:boolean;
  profils:[];
  //profils:Profil[];
  dateCreation:Date;
  dateModification:Date;
  droits: string[] = [];
  /**
   * Méthode permettant de retourner le nom prénom d'un utilisateur.
   */
  get label(): string {
    let nomPrenom = '';
    if (this.nom) {
      nomPrenom=nomPrenom.concat("",this.nom);
    }
    if (this.prenom) {
      nomPrenom=nomPrenom.concat(" ", this.prenom);
    }
    return nomPrenom;
  }
  static fromJson<T>(json: any, ctor?: NoParamConstructor<T>): T {
    const user: any = BuilderDtoJsonAbstract.fromJson(json, Utilisateur);

    // init valeur par defaut
    if (json.profils) {
      user.profils = Profil.fromJson(json.profils, Profil);
    }
    return user;
  }
  get roles(){
    return
  }
}
