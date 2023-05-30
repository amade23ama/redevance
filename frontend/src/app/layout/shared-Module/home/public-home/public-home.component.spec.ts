import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PublicHomeComponent } from './public-home.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {MaterialModule} from "../../../../material.module";
import {FlexLayoutModule} from "@angular/flex-layout";
import {AppConfigService} from "../../../../core/services/app-config.service";
import {HttpClient, HttpHandler} from "@angular/common/http";
import {UtilisateurService} from "../../../../core/services/utilisateur.service";
import {Globals} from "../../../../app.constants";

describe('PublicHomeComponent', () => {
  let component: PublicHomeComponent;
  let fixture: ComponentFixture<PublicHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PublicHomeComponent ],
      imports: [FormsModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        RouterTestingModule,
        MaterialModule, // Angular Material
        FlexLayoutModule,],
      providers: [AppConfigService,HttpClient,HttpHandler,UtilisateurService,Globals]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PublicHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
