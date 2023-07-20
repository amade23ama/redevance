import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {AppConfigService} from "../../../core/services/app-config.service";
import {Transporteur} from "../../../core/interfaces/transporteur";
import {ActionBtn} from "../../../core/interfaces/actionBtn";
import {Actions} from "../../../core/enum/actions";

@Component({
  selector: 'app-vehicule',
  templateUrl: './vehicule.component.html',
  styleUrls: ['./vehicule.component.scss']
})
export class VehiculeComponent implements OnInit {

  btns: ActionBtn[] = [];
  titre="Creer un Nouveau  Vehicule"
  id: FormControl = new FormControl();
  immatriculation: FormControl = new FormControl();
  nom: FormControl = new FormControl();
  volume: FormControl = new FormControl();
  dateCreation: FormControl = new FormControl();
  dateModification:FormControl = new FormControl();
  myform: FormGroup = this.builder.group({
    id: this. id,
    immatriculation: this.immatriculation,
    nom: this.nom,
    volume:this.volume,
    dateCreation: this.dateCreation,
    dateModification: this.dateModification,
  })
  constructor(public builder:FormBuilder,public appConfig:AppConfigService) {
  }
  ngOnInit(): void {
    this.initListbtns()
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
      const b= this.myform.value;
      //this.siteService.enregistrerSite(this.myform.value).subscribe()
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

