import { BuilderDtoJsonAbstract, NoParamConstructor } from "./BuilderDtoJsonAbstract";

/**
 * Produit
 */
export class Produit extends BuilderDtoJsonAbstract{


    /** identifiant */
    public id : number;

    /** identifiant */
    public nomSRC: string;

    /** identifiant */
    public nomNORM: string;

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
