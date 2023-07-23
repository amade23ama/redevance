import { ComponentFixture, TestBed } from '@angular/core/testing';
import {HttpClient, HttpHandler} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FlexLayoutModule} from "@angular/flex-layout";
import {DepotCreationComponent} from "./depot-creation.component";
import {MaterialModule} from "../../../material.module";
import {AppConfigService} from "../../../core/services/app-config.service";
import {UtilisateurService} from "../../../core/services/utilisateur.service";
import {Globals} from "../../../app.constants";
import {AuthService} from "../../../core/services/auth.service";

describe('DepotCreationComponent', () => {
  let component: DepotCreationComponent;
  let fixture: ComponentFixture<DepotCreationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DepotCreationComponent ],
      imports: [FormsModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        RouterTestingModule,
        MaterialModule, // Angular Material
        FlexLayoutModule,],
      providers: [AppConfigService,HttpClient,HttpHandler,UtilisateurService,Globals,AuthService]
    })
      .compileComponents();

    fixture = TestBed.createComponent(DepotCreationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
