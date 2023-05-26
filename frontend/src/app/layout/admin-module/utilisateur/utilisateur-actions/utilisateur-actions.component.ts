import {Component, OnInit} from '@angular/core';
import {ActionBtn} from "../../../../core/interfaces/actionBtn";
import {Actions} from "../../../../core/enum/actions";
import {AppConfigService} from "../../../../core/services/app-config.service";
import {Utilisateur} from "../../../../core/interfaces/utilisateur";
import {UtilisateurService} from "../../../../core/services/utilisateur.service";

@Component({
  selector: 'app-utilisateur-actions',
  templateUrl: './utilisateur-actions.component.html',
  styleUrls: ['./utilisateur-actions.component.scss']
})
export class UtilisateurActionsComponent implements OnInit{
  btns: ActionBtn[] = [];
  utilisateurCourant:Utilisateur
  constructor(public appConfig: AppConfigService,public utilisateurService:UtilisateurService) {
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
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.enregistrer'),
      Actions.ENREGISTRER, this.isEnrgBtnDisplayed(), false, true, true, 'save'));
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.modifier'),
      Actions.MODIFIER, this.isModifBtnAffiche(), false, true, true, 'create'));
    return this.btns;
  }
  isEnrgBtnDisplayed(){
    this.utilisateurCourant = this.utilisateurService.getUtilisateurCourant();
    if(!this.utilisateurCourant?.id){
      return true
    }
    return false
  }
  isModifBtnAffiche(){
    this.utilisateurCourant = this.utilisateurService.getUtilisateurCourant();
    if(this.utilisateurCourant?.id){
      return true
    }
    return false
  }

  utilisateurAction(event: Actions){

  }
}
