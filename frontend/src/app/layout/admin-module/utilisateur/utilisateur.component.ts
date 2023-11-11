import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { ModalService } from 'src/app/core/services/modal.service';
import { UrlService } from 'src/app/core/services/url.service';
import { Actions } from "../../../core/enum/actions";
import { DroitEnum } from "../../../core/enum/droit-enum";
import { ActionBtn } from "../../../core/interfaces/actionBtn";
import { Utilisateur } from "../../../core/interfaces/utilisateur";
import { AppConfigService } from "../../../core/services/app-config.service";
import { AuthService } from "../../../core/services/auth.service";
import { UtilisateurService } from "../../../core/services/utilisateur.service";

@Component({
  selector: 'app-utilisateur',
  templateUrl: './utilisateur.component.html',
  styleUrls: ['./utilisateur.component.scss']
})
export class UtilisateurComponent implements OnInit{
  utilisateurCourant:Utilisateur
  id: FormControl = new FormControl();
  prenom: FormControl=new FormControl('',
    {validators:[Validators.required, Validators.minLength(3),
      this.chiffreCaractValidator.bind(this)]});
  nom: FormControl=new FormControl('',
    {validators:[Validators.required, Validators.minLength(2),this.chiffreCaractValidator.bind(this)]});
  email : FormControl=new FormControl('', {
    validators: [Validators.required, Validators.email],
    asyncValidators: [this.checkEmail.bind(this)],
    updateOn: 'blur'
  });
  telephone: FormControl = new FormControl( '',{validators:[Validators.required, this.phoneNumberValidator.bind(this)]});
  active: FormControl = new FormControl(true);
  profils: FormControl = new FormControl('',{validators:[Validators.required]})
  titre:string
  btns: ActionBtn[] = [];
  myform :FormGroup
  isUpdate:boolean
  displayEmail=false;

  // indique si on est en modification
  isModeModification = false;

  constructor(public utilisateurService:UtilisateurService,private readonly activatedRoute: ActivatedRoute,
              public builder:FormBuilder,public appConfig: AppConfigService, public modalService: ModalService,
              private readonly authService: AuthService, private urlService: UrlService) {
    this.myform= this.builder.group({
      id:this.id,
      prenom:this.prenom,
      nom:this.nom,
      email:this.email,
      telephone:this.telephone,
      active: this.active,
      profils: this.profils
    });
  }

  ngOnInit(): void {
    this.activatedRoute.queryParams?.subscribe(async params => {
      if (params['contextInfo']) {
        this.titre="Utilisateur"
        this.isModeModification = true;
        this.utilisateurService.getUtilisateurParId(params['contextInfo']).subscribe(()=>{
          this.utilisateurCourant=this.utilisateurService.getUtilisateurCourant();
          this.myform.patchValue(this.utilisateurCourant)
          this.initListbtns();
          this.isUpdate=true
          this.majBtnActive()
          this.droit()
        })
      } else {
        this.isModeModification = false;
        this.initListbtns();
        this.isUpdate=false
        this.majBtnActive()
        this.droit()
      }
    });

  }
  sauvegarder() {
    const  val = this.myform.value
  }
  reset(formToReset:any){
    this.myform.controls[formToReset]?.setValue('');
  }
  private initListbtns() {
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.annuler'),
      Actions.ANNULER, true, false, false, true, 'keyboard_arrow_left', this.appConfig.getLabel('label.tooltip.user.bouton.annuler')));
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.enregistrer'),
      Actions.ENREGISTRER, this.isEnrgBtnDisplayed(), true, true, true, 'save', this.appConfig.getLabel('label.tooltip.user.bouton.enregistrer')));
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.modifier'),
      Actions.MODIFIER, this.isModifBtnAffiche(), true, true, true, 'create', this.appConfig.getLabel('label.tooltip.user.bouton.modifier')));
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
    if (event === Actions.ENREGISTRER) {
      this.utilisateurService.enregistrer(this.myform.value).subscribe((user)=>{
        this.displayEmail=true;
        let message = user.prenom + " " + user.nom +' a été habilité avec succès'
        this.modalService.ouvrirModalConfirmation(message);
      })
    }
    if (event === Actions.MODIFIER) {
      const b= this.myform.value;
      this.utilisateurService.enregistrer(this.myform.value).subscribe()
    }

    //Le click sur le bouton Annuler
    if (event === Actions.ANNULER) {
      this.modalService.ouvrirModaleAnnulation(this.urlService.getPreviousUrl(), this.isModeModification ? "modification de l'utilisateur" : "création de l'utilisateur"); //Ouverture de la modale d'annulation
    }

  }
  checkEmail(control:AbstractControl){
    return this.utilisateurService.emailCheck(control,this.utilisateurCourant?.id)
  }

  // Activation et désactivation des boutons en fonction des actions de l'utilisateur
  majBtnActive() {
    // Formulaire non valid
    this.myform?.valueChanges.subscribe((res) => {
      if (this.myform.invalid) {
        if (this.isModeModification) {
          this.majBtnState(Actions.ENREGISTRER, true, false);
          this.majBtnState(Actions.MODIFIER, true, true);
        } else {
          this.majBtnState(Actions.ENREGISTRER, true, true);
          this.majBtnState(Actions.MODIFIER, true, false);
        }
      }

      // Formulaire valid
      if (!this.myform.invalid) {
        if (this.isModeModification) {
          this.majBtnState(Actions.ENREGISTRER, true, false);
          this.majBtnState(Actions.MODIFIER, false, true);
        } else {
          this.majBtnState(Actions.ENREGISTRER, false, true);
          this.majBtnState(Actions.MODIFIER, true, false);
        }
      }
    })

  }

    droit(){
      if(this.authService.hasDroits(DroitEnum.CONSULT)){
        this.myform.disable()
        this.btns.forEach(b=>{b.disabled=true});
      }
    }

chiffreCaractValidator(control: AbstractControl) {
    const status = /[0-9!@#$%^&*()_+{}\[\]:;<>,.?~\\/-]/.test(control.value);
    return status ? { exclus: true } : null
  }
  chiffreValidator(control: AbstractControl) {
    const status = /[0-9!@#$%^&*()_+{}\[\]:;<>,.?~\\/-]/.test(control.value);
    return status ? { exclus: true } : null
  }
  phoneNumberValidator(control: AbstractControl){
    const phoneNumberPattern = /^[0-9]{9}$/; // Exemple : Format de numéro de téléphone à 9 chiffres: exemple 77 123 45 67
    const status  = phoneNumberPattern.test(control.value);
    return status ? null: { isPhoneNumberValid: true }
  }
  
  /** ouvrir Modale Annulation */
  majBtnState(a: Actions, disabled: boolean, display: boolean) {
    this.btns.forEach(b => {
      if (b.id === a) {
        b.disabled = disabled;
        b.display = display;
      }
    });
  }
}
