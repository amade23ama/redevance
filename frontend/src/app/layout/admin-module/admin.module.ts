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
import {UtilisateurUpdateComponent} from "./utilisateur-update/utilisateur-update.component";
import {LogsComponent} from "./logs/logs.component";
import {MonitoreComponent} from "./monitore/monitore.component";
import {AdminRouter} from "./admin.router";
import { SiteComponent } from './site/site.component';
import { VehiculeComponent } from './vehicule/vehicule.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
@NgModule({
  declarations: [
    UtilisateurUpdateComponent,
    LogsComponent,
    ConfigurationComponent,
    MonitoreComponent,
    SiteComponent,
    VehiculeComponent
  ],
  imports: [
    //AdminRouter,
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule

  ],
  exports: [
  ],
  providers: [],
})
export class AdminModule {
}
