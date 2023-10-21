import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { RegexConstantes } from 'src/app/core/constantes/regexConstantes';
import { ModalService } from 'src/app/core/services/modal.service';
import { UrlService } from 'src/app/core/services/url.service';
import { Actions } from "../../../core/enum/actions";
import { ActionBtn } from "../../../core/interfaces/actionBtn";
import { Vehicule } from "../../../core/interfaces/vehicule";
import { AppConfigService } from "../../../core/services/app-config.service";
import { VehiculeService } from "../../../core/services/vehicule.service";

@Component({
  selector: 'app-vehicule',
  templateUrl: './vehicule.component.html',
  styleUrls: ['./vehicule.component.scss']
})
export class VehiculeComponent implements OnInit {
  types=[{code:'S',libelle:'sociéte'},{code:'P',libelle:'particulier'}]
  btns: ActionBtn[] = [];
  titre="Créer un nouveau  vehicule"
  titreTransport="Créer un nouveau  transporteur"
  id: FormControl = new FormControl();
  immatriculation: FormControl = new FormControl('',[Validators.required]);
  nom: FormControl = new FormControl();
  volume: FormControl = new FormControl();
  dateCreation: FormControl = new FormControl();
  dateModification:FormControl = new FormControl();

  transId: FormControl = new FormControl();
  transPrenom: FormControl = new FormControl('', {validators: [Validators.pattern(RegexConstantes.REGEX_NOM_PRENOM)]});
  transNom: FormControl = new FormControl('', {validators: [Validators.pattern(RegexConstantes.REGEX_NOM_PRENOM)]});
  transType: FormControl = new FormControl();
  transAdresse: FormControl = new FormControl();
  transTelephone: FormControl = new FormControl();
  transEmail:FormControl = new FormControl('', {validators: [Validators.email, Validators.pattern(RegexConstantes.REGEX_MAIL)]});
  categorieId: FormControl = new FormControl();
  type: FormControl = new FormControl('',[Validators.required]);
  categorie:FormGroup = this.builder.group({
    id:this.categorieId,
    volume:this.volume,
    type:this.type
})
  transporteur: FormGroup = this.builder.group({
    id:this.transId ,
    adresse: this.transAdresse,
    nom: this.transNom,
    prenom:this.transPrenom,
    telephone:this.transTelephone,
    email:this.transEmail,
    type:this.transType
  });
  myform: FormGroup = this.builder.group({
    id: this. id,
    immatriculation: this.immatriculation,
    dateCreation: this.dateCreation,
    dateModification: this.dateModification,
    transporteur:this.transporteur,
    categorie:this.categorie
  })
  vehiculeCourant:Vehicule;

  // indique si on est en modification
  isModeModification = false;

  constructor(public builder:FormBuilder, public appConfig:AppConfigService,
              public vehiculeService:VehiculeService, private readonly activatedRoute: ActivatedRoute,
              public modalService: ModalService, public urlService: UrlService) {
  }

  ngOnInit(): void {
    this.activatedRoute.queryParams?.subscribe(async params => {
      if (params['contextInfo']) {
        //this.titre="Modification Site"
        this.isModeModification = true;
        this.vehiculeService.getVehiculeById(params['contextInfo']).subscribe(()=>{
          this.vehiculeCourant=this.vehiculeService.getVehiculeCourant()
          this.myform.patchValue(this.vehiculeCourant)
          this.majBtnActive()
        })
      } else {
        this.isModeModification = false;
        //this.titre="Creation Site";
        this.majBtnActive()
      }
      this.initListbtns();
    });
  }
  sauvegarder(){


  }

  reset(formToReset:string){
    this.myform.controls[formToReset]?.setValue('');
  }

  resetCategorie(formToReset:string){
    this.categorie.controls[formToReset]?.setValue('');
  }

  annuler(){
    console.log(" annuller")
  }

  private initListbtns() {
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.annuler'), Actions.ANNULER, true, false, false, true, 'keyboard_arrow_left'));
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.creer'), Actions.CREER, !this.isModeModification, true, true, true, 'save'));
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.modifier'), Actions.MODIFIER, this.isModeModification, true, true, true, 'create'));
    return this.btns;
  }

  // isEnrgBtnDisplayed
  isEnrgBtnDisplayed(){
    this.vehiculeCourant = this.vehiculeService.getVehiculeCourant();

    return !this.vehiculeCourant?.id ? true : false;
  }

  /** Action sur les boutons Enregistrer ou ANNULER */
  vehiculeAction(event: Actions){
    //Le click sur le bouton Enregistrer
    if (event === Actions.CREER || event === Actions.MODIFIER) {
      this.vehiculeService.enregistrerVehicule(this.myform.value).subscribe()
    }

    //Le click sur le bouton Annuler
    if (event === Actions.ANNULER) {
      this.modalService.ouvrirModaleAnnulation(this.urlService.getPreviousUrl(), this.isModeModification ? 'modification de véhicule' : 'création de véhicule'); //Ouverture de la modale d'annulation
    }
  }

// Activation et désactivation des boutons en fonction des actions de l'utilisateur
majBtnActive(){
  // Formulaire non valid
  this.myform?.valueChanges.subscribe((res)=>{
    if(this.myform.invalid){
        if (this.isModeModification) {
          this.majBtnState(Actions.CREER, true, false);
          this.majBtnState(Actions.MODIFIER, true, true);
        }else{
          this.majBtnState(Actions.CREER, true, true);
          this.majBtnState(Actions.MODIFIER, true, false);
        }
    }

    // Formulaire valid
    if(!this.myform.invalid){
        if (this.isModeModification) {
          this.majBtnState(Actions.CREER, true, false);
          this.majBtnState(Actions.MODIFIER, false, true);
        }else{
          this.majBtnState(Actions.CREER, false, true);
          this.majBtnState(Actions.MODIFIER, true, false);
        }
    }
  })

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

