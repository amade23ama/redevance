import { ComponentFixture, TestBed } from '@angular/core/testing';
import {HttpClient, HttpHandler} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FlexLayoutModule} from "@angular/flex-layout";
import {DepotComponent} from "./depot.component";
import {MaterialModule} from "../../material.module";
import {AppConfigService} from "../../core/services/app-config.service";
import {UtilisateurService} from "../../core/services/utilisateur.service";
import {AuthService} from "../../core/services/auth.service";
import {Globals} from "../../app.constants";

describe('LayoutComponent', () => {
  let component: DepotComponent;
  let fixture: ComponentFixture<DepotComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DepotComponent ],
      imports: [FormsModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        RouterTestingModule,
        MaterialModule, // Angular Material
        FlexLayoutModule,],
      providers: [AppConfigService,HttpClient,HttpHandler,UtilisateurService,Globals,AuthService]
    })
      .compileComponents();

    fixture = TestBed.createComponent(DepotComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
