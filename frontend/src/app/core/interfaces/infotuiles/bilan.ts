import {Campagne} from "./campagne";
import {BuilderDtoJsonAbstract, NoParamConstructor} from "../BuilderDtoJsonAbstract";
import {Detail} from "./detail";

export class Bilan  extends BuilderDtoJsonAbstract{
  annee: number;
  campagnes:Campagne[];
  description:string;
  static  fromJson<T>(json: any, ctor: NoParamConstructor<T>): T {
    const result: any = super.fromJson(json, ctor);
    const homeBilan: Bilan = result as Bilan;

    if (json.campagnes) {
      homeBilan.campagnes = json.campagnes.map((campagnesJson: any) => Campagne.fromJson(campagnesJson, Campagne));
    }
    return result;
  }
}
