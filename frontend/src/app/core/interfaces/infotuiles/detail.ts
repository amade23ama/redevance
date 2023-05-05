import {BuilderDtoJsonAbstract} from "../BuilderDtoJsonAbstract";

export class Detail extends BuilderDtoJsonAbstract{
  // @ts-ignore
  /** Type de détail. */
  typeDetail: string;

  /** Quantite. */
  quantite: number;
}
