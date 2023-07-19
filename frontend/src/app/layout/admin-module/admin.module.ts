import {NgModule} from "@angular/core";
import {HomeComponent} from "../shared-Module/home/home.component";
import {DisconnectedHomeComponent} from "../shared-Module/home/disconnected-home/disconnected-home.component";
import {ConnectedHomeComponent} from "../shared-Module/home/connected-home/connected-home.component";
import {PublicHomeComponent} from "../shared-Module/home/public-home/public-home.component";
import {DscomFooterComponent} from "../shared-Module/dscom-footer/dscom-footer.component";
import {DscomHeaderComponent} from "../shared-Module/dscom-header/dscom-header.component";
import {DscomMessageComponent} from "../shared-Module/dscom-message/dscom-message.component";
import {SpinnerComponent} from "../shared-Module/spinner/spinner.component";
import {ConfirmationDialogComponent} from "../shared-Module/dialog/confirmation-dialog/confirmation-dialog.component";
import {ActionBtnsComponent} from "../shared-Module/action-btns/action-btns.component";
import {CommonModule} from "@angular/common";
import {MaterialModule} from "../../material.module";
import {ConfigurationComponent} from "./configuration/configuration.component";
import {UtilisateurCreateComponent} from "./utilisateur/utilisateur-create/utilisateur-create.component";
import {LogsComponent} from "./logs/logs.component";
import {MonitoreComponent} from "./monitore/monitore.component";
import {AdminRouter} from "./admin.router";
import { SiteComponent } from './site/site.component';
import { VehiculeComponent } from './vehicule/vehicule.component';
import {UserModule} from "./utilisateur/utilisateur.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { TransporteurComponent } from './transporteur/transporteur.component';
import { StepperVehiculeComponent } from './vehicule/stepper/stepper-vehicule/stepper-vehicule.component';
import { StepperTransporteurComponent } from './vehicule/stepper/stepper-transporteur/stepper-transporteur.component';
import { StepperComponent } from './vehicule/stepper/stepper.component';
import {AdminComponent} from "./admin.component";
import {RouterModule, RouterOutlet} from "@angular/router";
import { UtilisateurActionsComponent } from './utilisateur/utilisateur-actions/utilisateur-actions.component';
import {NgxMatSelectSearchModule} from "ngx-mat-select-search";
import {ProduitComponent} from "./produit/produit.component";
import {SharedModule} from "../shared-Module/shared.module";
import {ExploitationComponent} from "./exploitation/exploitation.component";

@NgModule({
  declarations: [
    //UtilisateurCreateComponent,
    LogsComponent,
    ConfigurationComponent,
    MonitoreComponent,
    SiteComponent,
    VehiculeComponent,
    TransporteurComponent,
    StepperVehiculeComponent,
    StepperTransporteurComponent,
    StepperComponent,
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
  exports: [RouterModule, UtilisateurActionsComponent],
  providers: [],
})
export class AdminModule {
}
