import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AppConfigService} from "../../core/services/app-config.service";
import {UtilisateurService} from "../../core/services/utilisateur.service";

@Component({
  selector: 'app-dscom-login',
  templateUrl: './dscom-login.component.html',
  styleUrls: ['./dscom-login.component.scss']
})
export class DscomLoginComponent implements OnInit{
  @Output() formLogin = new EventEmitter<FormGroup>();

  propLogin: FormControl = new FormControl('admin',  {
    validators: [Validators.required],
    asyncValidators: [this.checkLogin.bind(this)],
    updateOn: 'blur'
  })
  propPassword: FormControl = new FormControl('admin')
  myform: FormGroup

  constructor(private builder: FormBuilder,public appConfig:AppConfigService,public utilisateurService:UtilisateurService) {
    this.myform= this.builder.group({
      login: this.propLogin,
      password: this.propPassword
    })
  }

  ngOnInit(): void {
  }
  login(){
    if (this.myform.valid) {
      // Emit an event with the form data
      this.formLogin.emit(this.myform);
    }
  }
  checkLogin(control:AbstractControl){
    return this.utilisateurService.checkLogin(control)
  }
}
