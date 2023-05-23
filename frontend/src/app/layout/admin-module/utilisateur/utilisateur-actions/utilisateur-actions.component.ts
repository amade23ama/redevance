import {Component, OnInit} from '@angular/core';
import {ActionBtn} from "../../../../core/interfaces/actionBtn";
import {Actions} from "../../../../core/enum/actions";
import {AppConfigService} from "../../../../core/services/app-config.service";

@Component({
  selector: 'app-utilisateur-actions',
  templateUrl: './utilisateur-actions.component.html',
  styleUrls: ['./utilisateur-actions.component.scss']
})
export class UtilisateurActionsComponent implements OnInit{
  btns: ActionBtn[] = [];
  constructor(public appConfig: AppConfigService) {
  }

  ngOnInit(): void {
    this.initListbtns();
  }
  /**
   * RDG_SAI_CNT_201 RDG_SAI_CNT_205 (V1.0 du 06/08/2021)
   * Methode permettant de retourner la liste des button action contrat
   * @private
   */
  private initListbtns() {

    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.valider'),
      Actions.VALIDER_SAISIE, false, false, true, true));

    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.enregistrer'),
      Actions.ENREGISTRER, this.isEnrgBtnDisplayed(), false, true, true, 'save'));

    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.desactiver'),
      Actions.SUPPRIMER, this.isSupprBtnAffiche(), false, true, true, 'delete'));

    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.modifier'),
      Actions.MODIFIER, this.isSupprBtnAffiche(), false, true, true, 'delete'));

    return this.btns;
  }
  isEnrgBtnDisplayed(){
    return true
  }
  isSupprBtnAffiche(){
    return true
  }
}
