import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransporteurComponent } from './transporteur.component';
import {HttpClient, HttpClientModule, HttpHandler} from "@angular/common/http";
import {MatCardModule} from "@angular/material/card";
import {AppConfigService} from "../../../core/services/app-config.service";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RouterTestingModule} from "@angular/router/testing";
import {MaterialModule} from "../../../material.module";
import {FlexLayoutModule} from "@angular/flex-layout";

describe('TransporteurComponent', () => {
  let component: TransporteurComponent;
  let fixture: ComponentFixture<TransporteurComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TransporteurComponent ],
      imports: [FormsModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        RouterTestingModule,
        MaterialModule, // Angular Material
        FlexLayoutModule,] ,// Angular Flex Layout],
      providers: [AppConfigService,HttpClient,HttpHandler]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransporteurComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
