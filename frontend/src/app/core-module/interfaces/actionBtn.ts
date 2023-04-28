import {Actions} from "../enum/actions";

/**
 * Classe action bouton.
 */
export class ActionBtn {

  /** Label du bouton. */
  label: string;

  /** Identification action. */
  id: Actions;

  /** Afficher. */
  display: boolean;

  /** Désactivation. */
  disabled: boolean;

  /** Bouton rose ou non. */
  pink: boolean;

  /** A droite ou à gauche. */
  rightside?: boolean;

  /** Icône. */
  icon?: string;

  /**
   * Constructeur.
   * @param label label.
   * @param id identification action.
   * @param display afficher.
   * @param disabled désactivation.
   * @param pink bouton rose ou non.
   * @param rightside à droite ou à gauche.
   * @param icon icône.
   */
  constructor(label: string, id: Actions, display: boolean, disabled: boolean, pink: boolean,
              rightside?: boolean, icon?: string) {
    this.label = label;
    this.id = id;
    this.display = display;
    this.disabled = disabled;
    this.pink = pink;
    this.rightside = rightside;
    this.icon = icon;
  }
}
