import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StepperVehiculeComponent } from './stepper-vehicule.component';
import {HttpClient, HttpClientModule, HttpHandler} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {MaterialModule} from "../../../../../material.module";
import {FlexLayoutModule} from "@angular/flex-layout";
import {AppConfigService} from "../../../../../core/services/app-config.service";
import {UtilisateurService} from "../../../../../core/services/utilisateur.service";

describe('StepperVehiculeComponent', () => {
  let component: StepperVehiculeComponent;
  let fixture: ComponentFixture<StepperVehiculeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StepperVehiculeComponent ],
      imports: [FormsModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        RouterTestingModule,
        MaterialModule, // Angular Material
        FlexLayoutModule,],
      providers: [AppConfigService,HttpClient,HttpHandler,UtilisateurService]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StepperVehiculeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
