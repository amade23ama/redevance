import {BuilderDtoJsonAbstract} from "./BuilderDtoJsonAbstract";

export class Site   extends BuilderDtoJsonAbstract{
  id: number;
  nom:string;
  localite:string;
  dateCreation:Date;
  dateModification:Date;
}
