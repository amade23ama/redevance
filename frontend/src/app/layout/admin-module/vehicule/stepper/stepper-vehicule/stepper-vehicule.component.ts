import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AppConfigService} from "../../../../../core/services/app-config.service";

@Component({
  selector: 'app-stepper-vehicule',
  templateUrl: './stepper-vehicule.component.html',
  styleUrls: ['./stepper-vehicule.component.scss']
})
export class StepperVehiculeComponent implements OnInit {


  id: FormControl = new FormControl();
  immatriculation: FormControl = new FormControl();
  nom: FormControl = new FormControl('',[Validators.required,Validators.minLength(3)]);
  volume: FormControl = new FormControl('',[Validators.required,Validators.min(3)]);
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
