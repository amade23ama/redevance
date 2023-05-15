import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AppConfigService} from "../../../core/services/app-config.service";

@Component({
  selector: 'app-transporteur',
  templateUrl: './transporteur.component.html',
  styleUrls: ['./transporteur.component.scss']
})

export class TransporteurComponent implements OnInit{

  id: FormControl = new FormControl();
  prenom: FormControl = new FormControl('', [Validators.required,Validators.minLength(3)]);
  nom: FormControl = new FormControl('', [Validators.required,Validators.minLength(3)]);
  type: FormControl = new FormControl('', [Validators.required,Validators.minLength(3)]);
  telephone: FormControl = new FormControl('', [Validators.min(0), Validators.minLength(9)]);
  email:FormControl = new FormControl('',[Validators.email]);
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
