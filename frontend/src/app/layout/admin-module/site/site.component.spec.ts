import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SiteComponent } from './site.component';
import {HttpClientModule} from "@angular/common/http";
import {CommonModule, NgIf, NgOptimizedImage} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ExtendedModule, FlexModule} from "@angular/flex-layout";
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MaterialModule} from "../../../material.module";
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

describe('SiteComponent', () => {
  let component: SiteComponent;
  let fixture: ComponentFixture<SiteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SiteComponent ],
      imports: [HttpClientModule,CommonModule,
        FormsModule,
        ReactiveFormsModule,
        FlexModule,
        MatButtonModule,
        MatCardModule,
        MatFormFieldModule,
        MatInputModule,
        ExtendedModule,
        MaterialModule,
        NgOptimizedImage,
        BrowserModule,
        BrowserAnimationsModule,
        NgIf,]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SiteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
