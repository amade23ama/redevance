import { Component, OnInit } from '@angular/core';
import { FormGroup } from "@angular/forms";
import { Router } from "@angular/router";
import { AuthService } from "../core/services/auth.service";
import { UrlService } from '../core/services/url.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent  implements OnInit{

  constructor(public authService :AuthService,
              private router:Router,  public urlService: UrlService) {
  }
  ngOnInit(): void {
  }

  login(myform: FormGroup){
    this.authService.authenticate(myform.value).subscribe((res)=>{
      this.router.navigate(["/"]);
    })
  }
}
