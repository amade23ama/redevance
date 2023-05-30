import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RechercheComponent } from './recherche.component';
import {HttpClient, HttpClientModule, HttpHandler} from "@angular/common/http";
import {RechercheRouter} from "./recherche.router";
import {MatTableModule} from "@angular/material/table";
import {NgForOf, NgIf} from "@angular/common";
import {MatIconModule} from "@angular/material/icon";
import {MaterialModule} from "../../material.module";
import {LayoutModule} from "../layout.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FlexLayoutModule} from "@angular/flex-layout";
import {AppConfigService} from "../../core/services/app-config.service";
import {UtilisateurService} from "../../core/services/utilisateur.service";
import {Globals} from "../../app.constants";

describe('RechercheComponent', () => {
  let component: RechercheComponent;
  let fixture: ComponentFixture<RechercheComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RechercheComponent ],
      imports: [FormsModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        RouterTestingModule,
        MaterialModule, // Angular Material
        FlexLayoutModule,],
      providers: [AppConfigService,HttpClient,HttpHandler,UtilisateurService,Globals]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RechercheComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
