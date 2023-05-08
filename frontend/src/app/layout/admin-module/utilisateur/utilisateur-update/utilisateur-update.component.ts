import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ParamService} from "../../../../core/services/param.service";
import {AppConfigService} from "../../../../core/services/app-config.service";

@Component({
  selector: 'app-utilisateur-update',
  templateUrl: './utilisateur-update.component.html',
  styleUrls: ['./utilisateur-update.component.scss']
})
export class UtilisateurUpdateComponent implements OnInit{

  prenom: FormControl = new FormControl('',[Validators.required,Validators.minLength(3)])
  nom: FormControl = new FormControl('',[Validators.required,Validators.minLength(2)])
  email: FormControl = new FormControl('',[Validators.required, Validators.email]);
  login: FormControl = new FormControl('',[Validators.required,Validators.minLength(6),Validators.maxLength(10)]);
  password:FormControl = new FormControl('',[Validators.required,Validators.minLength(6),Validators.maxLength(10)]);
  telephone:FormControl = new FormControl('',[Validators.required]);
  dateCreation:FormControl = new FormControl();
  dateModification:FormControl = new FormControl();
  active:FormControl = new FormControl();
  profil:FormControl = new FormControl('',[Validators.required])

  myform: FormGroup = this.builder.group({
    prenom: this.prenom,
    nom: this.nom,
    email: this.email,
    login: this.login,
    password: this.password,
    telephone:this.telephone,
    active:this.active,
    profil:this.profil,
    dateCreation:this.dateCreation,
    dateModification:this.dateModification
  })
  statusActive:boolean;
  labelval="";
  profils$=this.paramService.profils$
  constructor(private builder: FormBuilder,private paramService:ParamService,
              public appConfig:AppConfigService) {
  }
  ngOnInit(): void {
    this.paramService.chargementProfils().subscribe()
    this.active.valueChanges.subscribe((res)=>{
      this.statusActive =res
    })
  }
  reset(formToReset:string){
    this.myform.controls[formToReset]?.setValue('');
  }
  getStatus(){
    return this.labelval=this.statusActive==true?"activé":"désactivé";
  }
  sauvegarder(){
    console.log(" save")
  }
  annuler(){
    console.log(" annuller")
  }
}
