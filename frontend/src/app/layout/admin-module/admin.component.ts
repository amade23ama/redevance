import {AfterViewInit, ChangeDetectorRef, Component, OnInit} from "@angular/core";
import {AuthService} from "../../core/services/auth.service";
import {Globals} from "../../app.constants";
import {Router} from "@angular/router";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['admin.component.scss']
})

export class AdminComponent  implements OnInit , AfterViewInit{
  constructor(public authService: AuthService,public globals: Globals,
              public router:Router,private cdr: ChangeDetectorRef) {
  }
  ngOnInit(): void {
    this.cdr.detectChanges();
  }

  ngAfterViewInit(): void {
  }

}
