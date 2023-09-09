import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { RegexConstantes } from 'src/app/core/constantes/regexConstantes';
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
  immatriculation: FormControl = new FormControl();
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
  type: FormControl = new FormControl();
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

  constructor(public builder:FormBuilder,public appConfig:AppConfigService,
              public vehiculeService:VehiculeService,private readonly activatedRoute: ActivatedRoute) {
  }
  ngOnInit(): void {
    this.activatedRoute.queryParams?.subscribe(async params => {
      this.initListbtns();
      if (params['contextInfo']) {
        //this.titre="Modification Site"
        this.vehiculeService.getVehiculeById(params['contextInfo']).subscribe(()=>{
          this.vehiculeCourant=this.vehiculeService.getVehiculeCourant()
          this.myform.patchValue(this.vehiculeCourant)
          this.majBtnActive()
        })
      } else {
        //this.titre="Creation Site";
        this.majBtnActive()
      }
    });
  }
  sauvegarder(){


  }
  reset(formToReset:string){
    this.myform.controls[formToReset]?.setValue('');
  }
  annuler(){
    console.log(" annuller")
  }

  private initListbtns() {
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.annuler'),
      Actions.ANNULER, true, false, true, true, 'keyboard_arrow_left'));
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.enregistrer'),
      Actions.ENREGISTRER, this.isEnrgBtnDisplayed(), true, true, true, 'save'));
    return this.btns;
  }
  isEnrgBtnDisplayed(){
    return true
    /* this.utilisateurCourant = this.utilisateurService.getUtilisateurCourant();
     if(!this.utilisateurCourant?.id){
       return true
     }*/
    //return false
  }

  vehiculeAction(event: Actions){
    if (event === Actions.ENREGISTRER) {
      this.vehiculeService.enregistrerVehicule(this.myform.value).subscribe()
    }
  }
  majBtnActive(){
    this.myform?.valueChanges.subscribe((res)=>{
      if(this.myform.invalid){
        this.btns.forEach(b=>{
          b.disabled=true
        });
      }else{
        this.btns.forEach(b=>{
          b.disabled=false
        });
      }
    })
    if(!this.myform.invalid){
      this.btns.forEach(b=>{
        b.disabled=false
      });
    }
  }
}

