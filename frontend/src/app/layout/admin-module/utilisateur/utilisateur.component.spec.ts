import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UtilisateurComponent } from './utilisateur.component';
import {HttpClient, HttpClientModule, HttpHandler} from "@angular/common/http";
import {UtilisateurService} from "../../../core/services/utilisateur.service";
import {Globals} from "../../../app.constants";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {MaterialModule} from "../../../material.module";
import {FlexLayoutModule} from "@angular/flex-layout";
import {AppConfigService} from "../../../core/services/app-config.service";
import {ActionBtnsComponent} from "../../shared-Module/action-btns/action-btns.component";
import {
  FormulaireUtilisateurComponent
} from "../../shared-Module/formulaire-utilisateur/formulaire-utilisateur.component";

describe('UtilisateurComponent', () => {
  let component: UtilisateurComponent;
  let fixture: ComponentFixture<UtilisateurComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UtilisateurComponent,ActionBtnsComponent,FormulaireUtilisateurComponent],
      imports: [FormsModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        RouterTestingModule,
        MaterialModule, // Angular Material
        FlexLayoutModule,],
      providers: [AppConfigService,HttpClient,HttpHandler,UtilisateurService,Globals]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UtilisateurComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
