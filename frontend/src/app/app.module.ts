import {APP_INITIALIZER, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import {AppRoutingModule, routes} from './app-routing.module';
import { AppComponent } from './app.component';
import {MaterialModule} from "./material.module";
import {RouterModule} from "@angular/router";
import {RedevanceModule} from "./layout/redevanceModule/redevance.module";
import {Globals} from "./app.constants";
import {CommonModule} from "@angular/common";
import {SharedModule} from "./layout/shared-Module/shared.module";
import {AppConfigService} from "./core/services/app-config.service";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {JwtHelperService} from "@auth0/angular-jwt/lib/jwthelper.service";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {IntercepteurService} from "./core/intercepteur/intercepteur.service";
import {LoginModule} from "./login/login.module";
import {RouteGuard} from "./core/guards/route-guard.service";
import {AdminModule} from "./layout/admin-module/admin.module";
import {LayoutModule} from "./layout/layout.module";
import {NotificationService} from "./core/services/notification.service";
import {SessionTimerService} from "./core/services/Session.timer.service";
import {ReferenceService} from "./core/services/reference.service";
//import {AutorisationDirective} from "./core/directives/autorisation.directive";

@NgModule({
  declarations: [
    AppComponent,
    //AutorisationDirective,
  ],
  imports: [
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
  providers: [Globals, RouteGuard,ReferenceService, {
    provide: HTTP_INTERCEPTORS,
    useClass: IntercepteurService,
    multi: true
  },
    {
      provide: APP_INITIALIZER,
      useFactory: initLabel,
      deps: [AppConfigService],
      multi: true
    },
    {
      provide: APP_INITIALIZER,
      useFactory: initReference,
      deps: [ReferenceService],
      multi: true
    }
    ],
  exports: [
  ],
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
export function initReference(referenceService:ReferenceService){
  return ()=>{
    referenceService.getAllAnnee().subscribe()
  }
}
