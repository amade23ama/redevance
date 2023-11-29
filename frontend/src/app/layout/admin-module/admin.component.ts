import { AfterViewInit, ChangeDetectorRef, Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { UrlService } from "src/app/core/services/url.service";
import { Globals } from "../../app.constants";
import { AuthService } from "../../core/services/auth.service";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['admin.component.scss']
})

export class AdminComponent  implements OnInit , AfterViewInit{
  constructor(public authService: AuthService,public globals: Globals, public urlService: UrlService,
              public router:Router,private cdr: ChangeDetectorRef) {
  }
  ngOnInit(): void {
    this.cdr.detectChanges();
  }

  ngAfterViewInit(): void {
  }

}
