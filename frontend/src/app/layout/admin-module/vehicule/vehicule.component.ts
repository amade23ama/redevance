import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {AppConfigService} from "../../../core/services/app-config.service";
import {Transporteur} from "../../../core/interfaces/transporteur";

@Component({
  selector: 'app-vehicule',
  templateUrl: './vehicule.component.html',
  styleUrls: ['./vehicule.component.scss']
})
export class VehiculeComponent implements OnInit {


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

