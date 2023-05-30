import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {BrowserModule} from "@angular/platform-browser";
import {AppRoutingModule} from "./app-routing.module";
import {RedevanceModule} from "./layout/redevanceModule/redevance.module";
import {MaterialModule} from "./material.module";
import {CommonModule, DatePipe} from "@angular/common";
import {SharedModule} from "./layout/shared-Module/shared.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {LoginModule} from "./login/login.module";
import {LayoutModule} from "./layout/layout.module";
import {UtilisateurService} from "./core/services/utilisateur.service";
import {AuthService} from "./core/services/auth.service";
import {Globals} from "./app.constants";

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,HttpClientModule,
        BrowserModule,
        AppRoutingModule,
        RedevanceModule,
        MaterialModule,
        CommonModule,
        SharedModule,
        HttpClientModule,
        BrowserModule,
        BrowserAnimationsModule,
        MatButtonModule,
        MatIconModule,
        LoginModule,
        LayoutModule,
      ],
      declarations: [
        AppComponent
      ],
      providers: [UtilisateurService, DatePipe, HttpClient, AuthService, Globals]
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });


  /*it(`should have as title 'frontend'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('frontend');
  });
  */

  /*it('should render title', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.content span')?.textContent).toContain('frontend app is running!');
  });
  */
});
