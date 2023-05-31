import {BuilderDtoJsonAbstract, NoParamConstructor} from "./BuilderDtoJsonAbstract";

export class Profil extends BuilderDtoJsonAbstract{
  code:string;
  libelle:string;
  static fromJson<T>(json: any, ctor?: NoParamConstructor<T>): T {
    const profil: any = BuilderDtoJsonAbstract.fromJson(json, Profil);
    return profil;
  }
}
