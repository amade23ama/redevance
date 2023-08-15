import { RouterModule, Routes } from "@angular/router";
import { RechercheDepotComponent } from "./recherche-depot/recherche-depot.component";
import { RechercheExploitationComponent } from "./recherche-exploitation/recherche-exploitation.component";
import { RechercheProduitComponent } from "./recherche-produit/recherche-produit.component";
import { RechercheSiteComponent } from "./recherche-site/recherche-site.component";
import { RechercheUtilisateurComponent } from "./recherche-utilisateur/recherche-utilisateur.component";
import { RechercheVehiculeComponent } from "./recherche-vehicule/recherche-vehicule.component";
import { RechercheComponent } from "./recherche.component";
import {RechercheChargementComponent} from "./recherche-chargement/recherche-chargement.component";

const routes: Routes = [{
  path: '',
  component: RechercheComponent,
  children: [
    {
      path: 'user',
      component: RechercheUtilisateurComponent
    },
    {
      path: 'depots',
      component: RechercheDepotComponent
    },
    {
      path: 'site',
      component: RechercheSiteComponent
    },
    {
      path: 'produit',
      component: RechercheProduitComponent
    },
    {
      path: 'vehicule',
      component: RechercheVehiculeComponent
    },
    {
      path: 'exploitation',
      component: RechercheExploitationComponent
    },
    {
      path: 'chargement',
      component: RechercheChargementComponent
    },
  ]
}
]
export const RechercheRouter =RouterModule.forChild(routes)
