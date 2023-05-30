import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UtilisateurActionsComponent } from './utilisateur-actions.component';
import {AppConfigService} from "../../../../core/services/app-config.service";
import {HttpClient, HttpHandler} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {MaterialModule} from "../../../../material.module";
import {FlexLayoutModule} from "@angular/flex-layout";
import {Globals} from "../../../../app.constants";
import {UtilisateurService} from "../../../../core/services/utilisateur.service";
import {ActionBtnsComponent} from "../../../shared-Module/action-btns/action-btns.component";

describe('UtilisateurActionsComponent', () => {
  let component: UtilisateurActionsComponent;
  let fixture: ComponentFixture<UtilisateurActionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UtilisateurActionsComponent,ActionBtnsComponent ],
      imports: [FormsModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        RouterTestingModule,
        MaterialModule, // Angular Material
        FlexLayoutModule,],
      providers: [AppConfigService,HttpClient,HttpHandler,UtilisateurService,Globals]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UtilisateurActionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
