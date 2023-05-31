import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DscomLoginComponent } from './dscom-login.component';
import {HttpClient, HttpClientModule, HttpHandler} from "@angular/common/http";
import {AbstractControl, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {MaterialModule} from "../../material.module";
import {FlexLayoutModule} from "@angular/flex-layout";
import {AppConfigService} from "../../core/services/app-config.service";
import {UtilisateurService} from "../../core/services/utilisateur.service";
import {Globals} from "../../app.constants";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import { async } from '@angular/core/testing';


describe('DscomLoginComponent', () => {
  let component: DscomLoginComponent;
  let fixture: ComponentFixture<DscomLoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DscomLoginComponent ],
      imports: [FormsModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        RouterTestingModule,
        MaterialModule,
        FlexLayoutModule,HttpClientModule],
      providers: [AppConfigService,UtilisateurService,Globals]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DscomLoginComponent);
   component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    console.error('test')
    expect(component).toBeTruthy();
  });
  it('should have a valid login form', () => {
    const form = component.myform;
    expect(form.valid).toBeFalsy();
  });
  it('should require email and password fields', () => {
    const form = component.myform;
    const login = form.controls['login'];
    const password = form.controls['password'];
    expect(login.valid).toBeFalsy();
    const  val=login
    expect(login.errors['required']).toBeTruthy();
    expect(password.valid).toBeFalsy();
    expect(password.errors['required']).toBeTruthy();
  });

  // @ts-ignore
  it('should validate email format', () => {
    const form = component.myform;
    const login = form.controls['login'];
    form.patchValue({login: 'only',password:'cvfcf'});
    expect(login.errors).toBeNull();
  });

});
