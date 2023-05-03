import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AppConfigService} from "../../core/services/app-config.service";

@Component({
  selector: 'app-dscom-login',
  templateUrl: './dscom-login.component.html',
  styleUrls: ['./dscom-login.component.scss']
})
export class DscomLoginComponent implements OnInit{
  @Output() formLogin = new EventEmitter<FormGroup>();

  propLogin: FormControl = new FormControl("eve.holt@reqres.in", {
    validators: [Validators.required]
  })
  propPassword: FormControl = new FormControl("cityslicka")
  myform: FormGroup

  constructor(private builder: FormBuilder,public appConfig:AppConfigService) {
    this.myform= this.builder.group({
      email: this.propLogin,
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
}
