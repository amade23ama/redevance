import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DscomHeaderComponent } from './dscom-header.component';
import {AppConfigService} from "../../../core/services/app-config.service";
import {HttpClient, HttpHandler} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {MaterialModule} from "../../../material.module";
import {FlexLayoutModule} from "@angular/flex-layout";
import {UtilisateurService} from "../../../core/services/utilisateur.service";
import {Globals} from "../../../app.constants";

describe('DscomHeaderComponent', () => {
  let component: DscomHeaderComponent;
  let fixture: ComponentFixture<DscomHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DscomHeaderComponent ],
      imports: [FormsModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        RouterTestingModule,
        MaterialModule, // Angular Material
        FlexLayoutModule,],
      providers: [AppConfigService,HttpClient,HttpHandler,UtilisateurService,Globals]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DscomHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
