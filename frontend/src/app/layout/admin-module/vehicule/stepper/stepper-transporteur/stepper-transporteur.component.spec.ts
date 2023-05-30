import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StepperTransporteurComponent } from './stepper-transporteur.component';
import {HttpClient, HttpClientModule, HttpHandler} from "@angular/common/http";
import {UtilisateurService} from "../../../../../core/services/utilisateur.service";
import {Globals} from "../../../../../app.constants";
import {AppConfigService} from "../../../../../core/services/app-config.service";
import {AuthService} from "../../../../../core/services/auth.service";
import {CommonModule, NgIf, NgOptimizedImage} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ExtendedModule, FlexLayoutModule, FlexModule} from "@angular/flex-layout";
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MaterialModule} from "../../../../../material.module";
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";

describe('StepperTransporteurComponent', () => {
  let component: StepperTransporteurComponent;
  let fixture: ComponentFixture<StepperTransporteurComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StepperTransporteurComponent ],
      imports: [FormsModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        RouterTestingModule,
        MaterialModule, // Angular Material
        FlexLayoutModule,],
      providers: [AppConfigService,HttpClient,HttpHandler,UtilisateurService]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StepperTransporteurComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
