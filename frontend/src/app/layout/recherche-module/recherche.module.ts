import {NgModule} from "@angular/core";
import {RechercheRouter} from "./recherche.router";
import {RechercheComponent} from "./recherche.component";
import {RechercheUtilisateurComponent} from "./recherche-utilisateur/recherche-utilisateur.component";
import { RechercheSiteComponent } from './recherche-site/recherche-site.component';
import { RechercheProduitComponent } from './recherche-produit/recherche-produit.component';

@NgModule({
  declarations:[RechercheComponent,RechercheUtilisateurComponent, RechercheSiteComponent, RechercheProduitComponent],
  imports:[RechercheRouter],
  exports:[]
})
export class  RechercheModule{}
