import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import {AppRoutingModule, routes} from './app-routing.module';
import { AppComponent } from './app.component';
import {MaterialModule} from "./material.module";
import {RouterModule} from "@angular/router";
import {RedevanceModule} from "./redevanceModule/redevance.module";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RedevanceModule,
    MaterialModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
