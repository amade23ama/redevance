import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {MaterialModule} from "../../../material.module";
import { UtilisateurComponent } from './utilisateur.component';
import {UtilisateurCreateComponent} from "./utilisateur-create/utilisateur-create.component";
import {ReactiveFormsModule} from "@angular/forms";


@NgModule({
  declarations: [
    UtilisateurComponent,
    UtilisateurCreateComponent
  ],
  imports: [
    //AdminRouter,
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,

  ],
  exports: [
  ],
  providers: [],
})
export class UserModule {}
