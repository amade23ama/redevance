import {NgModule} from "@angular/core";
import {RechercheRouter} from "./recherche.router";
import {RechercheComponent} from "./recherche.component";
import {RechercheUtilisateurComponent} from "./recherche-utilisateur/recherche-utilisateur.component";
import { RechercheSiteComponent } from './recherche-site/recherche-site.component';
import { RechercheProduitComponent } from './recherche-produit/recherche-produit.component';
import {MatTableModule} from "@angular/material/table";
import {NgForOf, NgIf} from "@angular/common";
import {MatIconModule} from "@angular/material/icon";
import {MaterialModule} from "../../material.module";
import {LayoutModule} from "../layout.module";
import { RechercheVehiculeComponent } from './recherche-vehicule/recherche-vehicule.component';
import { RechercheExploitationComponent } from './recherche-exploitation/recherche-exploitation.component';

@NgModule({
  declarations:[RechercheComponent,RechercheUtilisateurComponent, RechercheSiteComponent, RechercheProduitComponent, RechercheVehiculeComponent, RechercheExploitationComponent],
  imports: [RechercheRouter, MatTableModule, NgForOf, MatIconModule, MaterialModule, NgIf, LayoutModule],
  exports:[]
})
export class  RechercheModule{}
