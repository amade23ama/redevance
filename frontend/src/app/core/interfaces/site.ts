import {BuilderDtoJsonAbstract, NoParamConstructor} from "./BuilderDtoJsonAbstract";

/** Site */
export class Site extends BuilderDtoJsonAbstract{

  /** id */
  id: number;

  /** nom */
  nom: string;

  /** localite */
  localite: string;

  /** dateCreation */
  dateCreation: Date;

  /** dateModification */
  dateModification: Date;
  static fromJson<T>(json: any, ctor?: NoParamConstructor<T>): T {
    const site: any = BuilderDtoJsonAbstract.fromJson(json, Site);
    return site;
  }
}
