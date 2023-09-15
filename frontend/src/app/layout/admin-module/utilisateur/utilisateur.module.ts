import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {MaterialModule} from "../../../material.module";
import { UtilisateurComponent } from './utilisateur.component';
import {UtilisateurCreateComponent} from "./utilisateur-create/utilisateur-create.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {SharedModule} from "../../shared-Module/shared.module";
import { UpdatePwdComponent } from './update-pwd/update-pwd.component';


@NgModule({
  declarations: [
    UtilisateurComponent,
    UtilisateurCreateComponent,
    UpdatePwdComponent,
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
  exports: [],
  providers: [],
})
export class UserModule {}
