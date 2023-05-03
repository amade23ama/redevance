import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AppConfigService} from "../core/services/app-config.service";
import {AuthService} from "../core/services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent  implements OnInit{

  constructor(public authService :AuthService,
              private router:Router) {
  }
  ngOnInit(): void {
  }

  login(myform: FormGroup){
    this.authService.authenticate(myform.value).subscribe((res)=>{
      this.router.navigate(["/"]);
    })
  }
}
