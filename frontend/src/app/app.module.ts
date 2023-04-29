import {APP_INITIALIZER, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import {AppRoutingModule, routes} from './app-routing.module';
import { AppComponent } from './app.component';
import {MaterialModule} from "./material.module";
import {RouterModule} from "@angular/router";
import {RedevanceModule} from "./redevanceModule/redevance.module";
import {Globals} from "./app.constants";
import {CommonModule} from "@angular/common";
import {SharedModule} from "./shared-Module/shared.module";
import {AppConfigService} from "./core-module/services/app-config.service";
import {HttpClientModule} from "@angular/common/http";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RedevanceModule,
    MaterialModule,
    CommonModule,
    SharedModule,
    HttpClientModule,
  ],
  providers: [Globals,
    {
      provide: APP_INITIALIZER,
      useFactory: initLabel,
      deps: [AppConfigService],
      multi: true
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }
export function initLabel(appConfig: AppConfigService) {
  return () => {
    // Chargement du label en local d'abord
    // Cela permet d'avoir les labels non vides si jamais
    // un probleme avec le label du serveur
    appConfig.loadLocalLabel().then(() => {
      // Chargement du label du serveur
      //appConfig.loadLabel();
    });
  };
}
