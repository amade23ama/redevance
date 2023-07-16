import { BuilderDtoJsonAbstract } from "./BuilderDtoJsonAbstract";

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
}
