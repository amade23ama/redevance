import { CommonModule, NgOptimizedImage } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatLegacyChipsModule } from "@angular/material/legacy-chips";
import { RouterOutlet } from "@angular/router";
import { AutorisationDirective } from "src/app/core/directives/autorisation.directive";
import { MaterialModule } from "../../material.module";
import { RedevanceModule } from "../redevanceModule/redevance.module";
import { ActionBtnsComponent } from './action-btns/action-btns.component';
import { ActionsCritereBtnsComponent } from "./actions-critere-btns/actions-critere-btns.component";
import { DscomExtensibleComponent } from "./dscom-extensible/dscom.extensible.component";
import { DscomFooterComponent } from './dscom-footer/dscom-footer.component';
import { DscomHeaderComponent } from './dscom-header/dscom-header.component';
import { DscomMessageComponent } from './dscom-message/dscom-message.component';
import { FormulaireUtilisateurComponent } from './formulaire-utilisateur/formulaire-utilisateur.component';
import { ConnectedHomeComponent } from './home/connected-home/connected-home.component';
import { DisconnectedHomeComponent } from './home/disconnected-home/disconnected-home.component';
import { HomeComponent } from './home/home.component';
import { PublicHomeComponent } from './home/public-home/public-home.component';
import { SpinnerComponent } from './spinner/spinner.component';
import {StringifyTemplatePipe} from "../../core/directives/stringify-template.pipe";

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
    //ConfirmationDialogComponent,
    ActionBtnsComponent,
    FormulaireUtilisateurComponent,
    ActionsCritereBtnsComponent,
    DscomExtensibleComponent,
    AutorisationDirective,
    StringifyTemplatePipe
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
    DscomExtensibleComponent,
    AutorisationDirective,
    StringifyTemplatePipe
  ],
  providers: [],
})
export class SharedModule {
}
