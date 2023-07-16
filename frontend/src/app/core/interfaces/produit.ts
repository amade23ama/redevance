import {BuilderDtoJsonAbstract, NoParamConstructor} from "./BuilderDtoJsonAbstract";

/**
 * Produit
 */
export class Produit extends BuilderDtoJsonAbstract{


    /** identifiant */
    public id : Number;

    /** identifiant */
    public nomSRC: String;

    /** identifiant */
    public nomNORM: String;

    /** identifiant */
    public densiteGRM: number;

    /** identifiant */
    public densiteKGM: number;

    /** identifiant */
    public dateCreation: Date;

    /** identifiant */
    public dateModification: Date;
  static fromJson<T>(json: any, ctor?: NoParamConstructor<T>): T {
    const produit: any = BuilderDtoJsonAbstract.fromJson(json, Produit);
    return produit;
  }
}
