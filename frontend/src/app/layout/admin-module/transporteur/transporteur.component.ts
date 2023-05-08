import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {AppConfigService} from "../../../core/services/app-config.service";

@Component({
  selector: 'app-transporteur',
  templateUrl: './transporteur.component.html',
  styleUrls: ['./transporteur.component.scss']
})

export class TransporteurComponent implements OnInit{

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
  }
  sauvegarder(){

  }
  reset(formToReset:string){
    this.myform.controls[formToReset]?.setValue('');
  }
  annuler(){
    console.log(" annuller")
  }


}
