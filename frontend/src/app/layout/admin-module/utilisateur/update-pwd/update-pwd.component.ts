import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Actions } from 'src/app/core/enum/actions';
import { ActionBtn } from 'src/app/core/interfaces/actionBtn';
import { Utilisateur } from 'src/app/core/interfaces/utilisateur';
import { AppConfigService } from 'src/app/core/services/app-config.service';
import { ModalService } from 'src/app/core/services/modal.service';
import { UrlService } from 'src/app/core/services/url.service';
import { UtilisateurService } from 'src/app/core/services/utilisateur.service';

@Component({
  selector: 'update-pwd',
  templateUrl: './update-pwd.component.html',
  styleUrls: ['./update-pwd.component.scss']
})
export class UpdatePwdComponent {
  // cacher les mdps à la saisie
  hideActual = true;
  hideNew = true;
  hideNewConfirm = true;

  //déclaration des boutons d'action
  boutons: ActionBtn[] = [];

  // formulaire groupe de changement de mdp
  changePwdForm :FormGroup
  actualPwd: FormControl = new FormControl('', {validators: [Validators.required, this.isPwdActual.bind(this)]});
  newPwd: FormControl = new FormControl('', {validators: [Validators.required, Validators.minLength(8)]});
  confirmPwd: FormControl = new FormControl('', {validators: [this.isNotIdentique.bind(this)]});

  // utilisateur Courant
  utilisateurCourant: Utilisateur;

  constructor(private readonly activatedRoute: ActivatedRoute, public appConfig: AppConfigService, public fb: FormBuilder, 
    private utilisateurService: UtilisateurService, public modalService: ModalService, public urlService: UrlService) {
    this.utilisateurCourant = this.utilisateurService.getUtilisateurCourant();
    this.changePwdForm = this.fb.group({
      actualPwd: this.actualPwd,
      newPwd: this.newPwd,
      confirmPwd: this.confirmPwd,
      });

 }

 ngOnInit(): void {

  this.activatedRoute.queryParams?.subscribe(async params => {
    if (params['contextInfo']) {
      this.utilisateurService.getUtilisateurParId(params['contextInfo']).subscribe(()=>{
        this.utilisateurCourant=this.utilisateurService.getUtilisateurCourant();
        this.changePwdForm.patchValue(this.utilisateurCourant);
      })
    }
  });

  this.initListbtns();
  this.majBtnActive();
 }

 /**
  * initListbtns
  * @returns 
  */
  private initListbtns() {
    this.boutons.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.annuler'), Actions.ANNULER, true, false, true, true, 'keyboard_arrow_left'));
    this.boutons.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.enregistrer'), Actions.ENREGISTRER, true, true, true, true, 'save'));
    return this.boutons;
  }

  /** utilisateurAction */
  utilisateurAction(event: Actions){
    // ENREGISTRER
    if (event === Actions.ENREGISTRER) {
      let user = this.utilisateurCourant;
      user.password = this.newForm.value;
      this.utilisateurService.enregistrer(user).subscribe((user)=>{
        this.majBtnState(Actions.ENREGISTRER, true)
        this.modalService.ouvrirModalConfirmation('Mot de passe modifié' );
      })
    }

    //Le click sur le bouton Annuler
    if (event === Actions.ANNULER) {
      this.modalService.ouvrirModaleAnnulation(this.urlService.getPreviousUrl(), 'modification du mot de passe' ); //Ouverture de la modale d'annulation
    }
  }

  majBtnActive() {
    this.changePwdForm?.valueChanges.subscribe((res) => {
      if (this.changePwdForm.invalid) {
        this.majBtnState(Actions.ENREGISTRER, true)
      }else{
        this.majBtnState(Actions.ENREGISTRER, false);
      }
    })
  }

  majBtnState(a: Actions, disabled: boolean) {
    this.boutons.forEach(b => {
      if (b.id === a) {
        b.disabled = disabled;
      }
    });
  }

  /**
   * isPwdActual
   * @param control isPwdActual
   * @returns 
   */
  isPwdActual(control: AbstractControl){
    return this.actualForm?.value === this.utilisateurCourant?.password ? null: { isNotActual: true }
  }

  /**
   * isNotIdentique
   * @param control 
   * @returns 
   */
  isNotIdentique(control: AbstractControl){
    return this.newForm?.value === this.confirmForm?.value ? null: { isNotIdentique: true }
  }

  get actualForm(): AbstractControl { return this.changePwdForm?.get("actualPwd");}
  get newForm(): AbstractControl { return this.changePwdForm?.get("newPwd");}
  get confirmForm(): AbstractControl { return this.changePwdForm?.get("confirmPwd");}

}
