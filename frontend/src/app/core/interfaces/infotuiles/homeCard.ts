import {BuilderDtoJsonAbstract, NoParamConstructor} from "../BuilderDtoJsonAbstract";
import {Detail} from "./detail";
import {Campagne} from "./campagne";

export class HomeCard extends BuilderDtoJsonAbstract{

  /** Type de la tuile. */
  typeTuile: string;

  /** Valeur. */
  valeur: number;

  /** Details d'une tuile. */
  details: Detail[];

  /** Campagnes d'une tuile. */
  campagnes: Campagne[];

  /**
   * From json custom pour gérer les objets inclus
   * @param json Json à parser
   * @param ctor constructeur sans parametre
   * @returns Objet HomeCard
   */
  static  fromJson<T>(json: any, ctor: NoParamConstructor<T>): T {
    const result: any = super.fromJson(json, ctor);
    const homeCard: HomeCard = result as HomeCard;

    if (json.details) {
      homeCard.details = json.details.map((detailsJson: any) => Detail.fromJson(detailsJson, Detail));
    }
    if (json.campagnes) {
      homeCard.campagnes = json.campagnes.map((campagnesJson: any) => Campagne.fromJson(campagnesJson, Campagne));
    }
    return result;
  }
}
