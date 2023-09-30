import {BuilderDtoJsonAbstract} from "./BuilderDtoJsonAbstract";

export class Categorie extends BuilderDtoJsonAbstract{
  id:number;
  type:number;
  volume:number;
  dateCreation:Date;
  dateModification:Date;
}
