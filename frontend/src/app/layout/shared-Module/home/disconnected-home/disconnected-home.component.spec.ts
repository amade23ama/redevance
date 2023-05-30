import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisconnectedHomeComponent } from './disconnected-home.component';
import {HttpClient, HttpClientModule, HttpHandler} from "@angular/common/http";
import {CommonModule, NgIf, NgOptimizedImage} from "@angular/common";
import {FormBuilder, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ExtendedModule, FlexLayoutModule, FlexModule} from "@angular/flex-layout";
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MaterialModule} from "../../../../material.module";
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {AuthService} from "../../../../core/services/auth.service";
import {AppConfigService} from "../../../../core/services/app-config.service";
import {UtilisateurService} from "../../../../core/services/utilisateur.service";
import {Globals} from "../../../../app.constants";
import {RouterTestingModule} from "@angular/router/testing";

describe('DisconnectedHomeComponent', () => {
  let component: DisconnectedHomeComponent;
  let fixture: ComponentFixture<DisconnectedHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DisconnectedHomeComponent ],

      imports: [FormsModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        RouterTestingModule,
        MaterialModule, // Angular Material
        FlexLayoutModule,],
      providers: [FormBuilder,AppConfigService,HttpClient,HttpHandler,UtilisateurService,Globals]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DisconnectedHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
