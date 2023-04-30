import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AppConfigService} from "../../core-module/services/app-config.service";

@Component({
  selector: 'app-dscom-login',
  templateUrl: './dscom-login.component.html',
  styleUrls: ['./dscom-login.component.scss']
})
export class DscomLoginComponent   implements OnInit{

  propLogin: FormControl = new FormControl("", {
    validators: [Validators.required]
  })
  propPassword: FormControl = new FormControl("")
  myform: FormGroup

  constructor(private builder: FormBuilder,public appConfig:AppConfigService) {
    this.myform= this.builder.group({
      login: this.propLogin,
      password: this.propPassword
    })
  }
  ngOnInit(): void {
  }
  login(){

  }
}
