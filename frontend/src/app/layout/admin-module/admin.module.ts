import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {MaterialModule} from "../../material.module";
import {ConfigurationComponent} from "./configuration/configuration.component";
import {LogsComponent} from "./logs/logs.component";
import {MonitoreComponent} from "./monitore/monitore.component";
import {AdminRouter} from "./admin.router";
import { SiteComponent } from './site/site.component';
import { VehiculeComponent } from './vehicule/vehicule.component';
import {UserModule} from "./utilisateur/utilisateur.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AdminComponent} from "./admin.component";
import {RouterModule, RouterOutlet} from "@angular/router";
import {NgxMatSelectSearchModule} from "ngx-mat-select-search";
import {ProduitComponent} from "./produit/produit.component";
import {SharedModule} from "../shared-Module/shared.module";
import {ExploitationComponent} from "./exploitation/exploitation.component";

@NgModule({
  declarations: [
    LogsComponent,
    ConfigurationComponent,
    MonitoreComponent,
    SiteComponent,
    VehiculeComponent,
    AdminComponent,
    ProduitComponent,
    ExploitationComponent
  ],
  imports: [

    AdminRouter,
    CommonModule,
    MaterialModule,
    UserModule,
    FormsModule,
    ReactiveFormsModule,
    RouterOutlet,
    NgxMatSelectSearchModule,
    SharedModule,
  ],
  exports: [RouterModule],
  providers: [],
})
export class AdminModule {
}
