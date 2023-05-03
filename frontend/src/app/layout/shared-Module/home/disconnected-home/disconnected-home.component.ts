import {Component, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {AuthService} from "../../../../core/services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-disconnected-home',
  templateUrl: './disconnected-home.component.html',
  styleUrls: ['./disconnected-home.component.scss']
})
export class DisconnectedHomeComponent implements OnInit  {
  constructor(public authService :AuthService,
              private router:Router) {
  }
  ngOnInit(): void {
  }

  login(myform: FormGroup){
    this.authService.authenticate(myform.value).subscribe((res)=>{
      this.router.navigate(["/home"]);
    })
  }
}
