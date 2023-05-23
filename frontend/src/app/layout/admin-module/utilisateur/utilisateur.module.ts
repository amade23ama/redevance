import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {MaterialModule} from "../../../material.module";
import { UtilisateurComponent } from './utilisateur.component';
import {UtilisateurCreateComponent} from "./utilisateur-create/utilisateur-create.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AdminModule} from "../admin.module";
import {UtilisateurActionsComponent} from "./utilisateur-actions/utilisateur-actions.component";
import {SharedModule} from "../../shared-Module/shared.module";


@NgModule({
  declarations: [
    UtilisateurComponent,
    UtilisateurCreateComponent,
    UtilisateurActionsComponent,
  ],
  imports: [
    //AdminRouter,
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    SharedModule,
    CommonModule,
    FormsModule,

  ],
  exports: [UtilisateurActionsComponent,],
  providers: [],
})
export class UserModule {}
