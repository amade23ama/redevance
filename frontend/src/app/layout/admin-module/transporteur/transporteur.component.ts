import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {AppConfigService} from "../../../core/services/app-config.service";
import {ActionBtn} from "../../../core/interfaces/actionBtn";
import {Actions} from "../../../core/enum/actions";

@Component({
  selector: 'app-transporteur',
  templateUrl: './transporteur.component.html',
  styleUrls: ['./transporteur.component.scss']
})

export class TransporteurComponent implements OnInit{
  types=[{code:'S',libelle:'societe'},{code:'P',libelle:'particulier'}]
  btns: ActionBtn[] = [];
  titre="Creer un Nouveau  Transporteur"
  id: FormControl = new FormControl();
  prenom: FormControl = new FormControl();
  nom: FormControl = new FormControl();
  type: FormControl = new FormControl();
  telephone: FormControl = new FormControl();
  email:FormControl = new FormControl();
  adresse: FormControl = new FormControl();
  dateCreation: FormControl = new FormControl();
  dateModification:FormControl = new FormControl();
  myform: FormGroup = this.builder.group({
    id: this. id,
    prenom: this.prenom,
    nom: this.nom,
    telephone:this.telephone,
    email:this.email,
    type:this.type,
    adresse:this.adresse,
    dateCreation: this.dateCreation,
    dateModification: this.dateModification,
  })
constructor(public builder:FormBuilder,public appConfig:AppConfigService) {
}
  ngOnInit(): void {
    this.initListbtns()
    this.majBtnActive()
    this.type.valueChanges.subscribe( (code:any)=>{
        if(code=='S'){
          this.prenom.disable();
          this.majBtnActive()
        }else {
          this.prenom.enable();
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

  transporteurAction(event: Actions){
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
