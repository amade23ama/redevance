import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StepperComponent } from './stepper.component';
import {HttpClient, HttpClientModule, HttpHandler} from "@angular/common/http";
import {ChangeDetectorRef} from "@angular/core";
import {AppConfigService} from "../../../../core/services/app-config.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {MaterialModule} from "../../../../material.module";
import {FlexLayoutModule} from "@angular/flex-layout";
import {UtilisateurService} from "../../../../core/services/utilisateur.service";
import {StepperTransporteurComponent} from "./stepper-transporteur/stepper-transporteur.component";
import {StepperVehiculeComponent} from "./stepper-vehicule/stepper-vehicule.component";

describe('StepperComponent', () => {
  let component: StepperComponent;
  let fixture: ComponentFixture<StepperComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StepperComponent,StepperTransporteurComponent,StepperVehiculeComponent ],
      imports: [FormsModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        RouterTestingModule,
        MaterialModule, // Angular Material
        FlexLayoutModule,],
      providers: [AppConfigService,HttpClient,HttpHandler,UtilisateurService]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StepperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
