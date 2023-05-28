import {LoginComponent} from "./login.component";
import {CommonModule, NgIf, NgOptimizedImage} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgModule} from "@angular/core";
import {ExtendedModule, FlexModule} from "@angular/flex-layout";
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import { DscomLoginComponent } from './dscom-login/dscom-login.component';
import { DscomInfoComponent } from './dscom-info/dscom-info.component';
import {MaterialModule} from "../material.module";
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

@NgModule({
  declarations: [
    LoginComponent,
    DscomLoginComponent,
    DscomInfoComponent
  ],
  imports: [
    CommonModule,
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
    NgIf,
  ],

})
export class LoginModule { }
