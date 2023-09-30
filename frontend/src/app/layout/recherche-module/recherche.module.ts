import {NgModule} from "@angular/core";
import {RechercheRouter} from "./recherche.router";
import {RechercheComponent} from "./recherche.component";
import {RechercheUtilisateurComponent} from "./recherche-utilisateur/recherche-utilisateur.component";
import { RechercheSiteComponent } from './recherche-site/recherche-site.component';
import { RechercheProduitComponent } from './recherche-produit/recherche-produit.component';
import {MatTableModule} from "@angular/material/table";
import {AsyncPipe, NgForOf, NgIf} from "@angular/common";
import {MatIconModule} from "@angular/material/icon";
import {MaterialModule} from "../../material.module";
import {LayoutModule} from "../layout.module";
import { RechercheVehiculeComponent } from './recherche-vehicule/recherche-vehicule.component';
import { RechercheExploitationComponent } from './recherche-exploitation/recherche-exploitation.component';
import {RechercheChargementComponent} from "./recherche-chargement/recherche-chargement.component";
import {SharedModule} from "../shared-Module/shared.module";
import {RechercheDepotComponent} from "./recherche-depot/recherche-depot.component";
import {RechercheChargementDepotComponent} from "./recherche-chargement-depot/recherche-chargement-depot.component";
import {RechercheCategorieComponent} from "./recherche-categorie/recherche-categorie.component";

@NgModule({
  declarations:[RechercheComponent,RechercheUtilisateurComponent, RechercheSiteComponent,
    RechercheProduitComponent, RechercheVehiculeComponent, RechercheExploitationComponent,
    RechercheChargementComponent,RechercheDepotComponent,RechercheChargementDepotComponent,
    RechercheCategorieComponent],
  imports: [RechercheRouter, MatTableModule, NgForOf,
    MatIconModule, MaterialModule, NgIf, LayoutModule, AsyncPipe, SharedModule],
  exports:[]
})
export class  RechercheModule{}
