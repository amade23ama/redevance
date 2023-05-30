import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UtilisateurCreateComponent } from './utilisateur-create.component';
import {HttpClient, HttpClientModule, HttpHandler} from "@angular/common/http";
import {AppConfigService} from "../../../../core/services/app-config.service";
import {Globals} from "../../../../app.constants";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {MaterialModule} from "../../../../material.module";
import {FlexLayoutModule} from "@angular/flex-layout";

describe('UtilisateurCreateComponent', () => {
  let component: UtilisateurCreateComponent;
  let fixture: ComponentFixture<UtilisateurCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UtilisateurCreateComponent ],
      imports: [FormsModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        RouterTestingModule,
        MaterialModule, // Angular Material
        FlexLayoutModule,],
      providers: [AppConfigService,HttpClient,HttpHandler,Globals]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UtilisateurCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
