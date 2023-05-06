import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators, ReactiveFormsModule} from "@angular/forms";
import { AppConfigService } from 'src/app/core/services/app-config.service';
import { SiteService } from 'src/app/core/services/site.service';

@Component({
  selector: 'app-site',
  templateUrl: './site.component.html',
  styleUrls: ['./site.component.scss']
})
export class SiteComponent implements OnInit{

  // @Output() formLogin = new EventEmitter<FormGroup>();

  siteName: FormControl = new FormControl("Site")
  locality: FormControl = new FormControl("thiès")
  mySiteform: FormGroup
  
  constructor(private builder: FormBuilder,public appConfig:AppConfigService, private siteService : SiteService) {
    console.log("appConfig", appConfig)
    
    this.mySiteform= this.builder.group({
      site: this.siteName,
      localite: this.locality
    })
  }
  ngOnInit(): void {
    console.error(" log")
  }

  siteDataInput(){
    if (this.mySiteform.valid) {
      // Emit an event with the form data
      console.log("this.mySiteform", this.mySiteform)
      // this.formLogin.emit(this.mySiteform);
      this.siteService.testMyFunction(this.mySiteform.value )
    }
  }

}
