import {BuilderDtoJsonAbstract, NoParamConstructor} from "./BuilderDtoJsonAbstract";

export class AutocompleteRecherche extends  BuilderDtoJsonAbstract{
  id:string;
  libelle:string;
  origine:string;
  typeClass:any;
  static fromJson<T>(json: any, ctor?: NoParamConstructor<T>): T {
    const result: any = BuilderDtoJsonAbstract.fromJson(json, AutocompleteRecherche);
    return result;
  }

}
