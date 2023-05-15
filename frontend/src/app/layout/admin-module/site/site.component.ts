import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ParamService} from "../../../core/services/param.service";
import {AppConfigService} from "../../../core/services/app-config.service";

@Component({
  selector: 'app-site',
  templateUrl: './site.component.html',
  styleUrls: ['./site.component.scss']
})
export class SiteComponent implements OnInit{
  id: FormControl = new FormControl()
  nom: FormControl = new FormControl('',[Validators.required,Validators.minLength(3)])
  localite: FormControl = new FormControl();
  dateCreation: FormControl = new FormControl();
  dateModification:FormControl = new FormControl();
  myform: FormGroup = this.builder.group({
    id: this. id,
    nom: this.nom,
    localite: this.localite,
    dateCreation: this.dateCreation,
    dateModification: this.dateModification,
  })


  constructor(private builder: FormBuilder,
              public appConfig:AppConfigService) {
  }
  ngOnInit(): void {
    console.error(" log")
  }
  sauvegarder(){

  }
  reset(formToReset:string){
    this.myform.controls[formToReset]?.setValue('');
  }
}
