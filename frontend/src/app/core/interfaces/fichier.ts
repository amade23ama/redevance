import {BuilderDtoJsonAbstract} from "./BuilderDtoJsonAbstract";

export class Fichier extends BuilderDtoJsonAbstract{


  /**
   * Identifiant du fichier.
   */
  id:number;

  /**
   * The nom.
   */
 nom:string;

  /**
   * The extension.
   */
 extension:string;

  /**
   * Attribut définissant la date de création du fichier.
   */
  dateCreation:Date;

  /**
   * The content.
   */
  content: string;
}
