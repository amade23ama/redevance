import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {MaterialModule} from "../../../material.module";
import { UtilisateurComponent } from './utilisateur.component';
import {UtilisateurUpdateComponent} from "./utilisateur-update/utilisateur-update.component";
import {ReactiveFormsModule} from "@angular/forms";


@NgModule({
  declarations: [
    UtilisateurComponent,
    UtilisateurUpdateComponent
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
