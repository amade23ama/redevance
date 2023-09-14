import {AutocompleteRecherche} from "./autocomplete.recherche";
import {BuilderDtoJsonAbstract, NoParamConstructor} from "./BuilderDtoJsonAbstract";

export class CritereRecherche extends BuilderDtoJsonAbstract{
  autocompleteRecherches:AutocompleteRecherche[];
  dateDebut: Date;
  dateFin:Date;
  page: number;
  size: number;
  static fromJson<T>(json: any, ctor?: NoParamConstructor<T>): T {
    const result: any = BuilderDtoJsonAbstract.fromJson(json, CritereRecherche);
    return result;
  }
  toJson(): any {
    return {
      ...super.toJson(),
      autocompleteRecherches: this.autocompleteRecherches ?
        this.autocompleteRecherches.map(res=>res.toJson()) : null,
    };
  }
}
