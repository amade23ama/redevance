import {NgModule} from "@angular/core";
import { HomeComponent } from './home/home.component';
import { DisconnectedHomeComponent } from './home/disconnected-home/disconnected-home.component';
import { ConnectedHomeComponent } from './home/connected-home/connected-home.component';
import { PublicHomeComponent } from './home/public-home/public-home.component';
import { DscomFooterComponent } from './dscom-footer/dscom-footer.component';
import { DscomHeaderComponent } from './dscom-header/dscom-header.component';
import { DscomMessageComponent } from './dscom-message/dscom-message.component';
import { SpinnerComponent } from './spinner/spinner.component';
import { ConfirmationDialogComponent } from './dialog/confirmation-dialog/confirmation-dialog.component';
import { ActionBtnsComponent } from './action-btns/action-btns.component';
import {CommonModule, NgOptimizedImage} from "@angular/common";
import {MaterialModule} from "../../material.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RouterOutlet} from "@angular/router";
import {RedevanceModule} from "../redevanceModule/redevance.module";
import { FormulaireUtilisateurComponent } from './formulaire-utilisateur/formulaire-utilisateur.component';
import {ActionsCritereBtnsComponent} from "./actions-critere-btns/actions-critere-btns.component";
import {MatLegacyChipsModule} from "@angular/material/legacy-chips";
import {DscomExtensibleComponent} from "./dscom-extensible/dscom.extensible.component";

@NgModule({
  declarations: [
    HomeComponent,
    DisconnectedHomeComponent,
    ConnectedHomeComponent,
    PublicHomeComponent,
    DscomFooterComponent,
    DscomHeaderComponent,
    DscomMessageComponent,
    SpinnerComponent,
    ConfirmationDialogComponent,
    ActionBtnsComponent,
    FormulaireUtilisateurComponent,
    ActionsCritereBtnsComponent,
    DscomExtensibleComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    NgOptimizedImage,
    ReactiveFormsModule,
    RouterOutlet,
    RedevanceModule,
    FormsModule,
    MatLegacyChipsModule,
  ],
  exports: [
    DscomFooterComponent,
    SpinnerComponent,
    DscomHeaderComponent,
    ActionBtnsComponent,
    FormulaireUtilisateurComponent,
    ActionsCritereBtnsComponent,
    DscomExtensibleComponent
  ],
  providers: [],
})
export class SharedModule {
}
