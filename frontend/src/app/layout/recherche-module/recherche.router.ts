import {RouterModule, Routes} from "@angular/router";
import {AdminComponent} from "../admin-module/admin.component";
import {UtilisateurCreateComponent} from "../admin-module/utilisateur/utilisateur-create/utilisateur-create.component";
import {SiteComponent} from "../admin-module/site/site.component";
import {VehiculeComponent} from "../admin-module/vehicule/vehicule.component";
import {TransporteurComponent} from "../admin-module/transporteur/transporteur.component";
import {StepperComponent} from "../admin-module/vehicule/stepper/stepper.component";
import {RechercheComponent} from "./recherche.component";
import {RechercheUtilisateurComponent} from "./recherche-utilisateur/recherche-utilisateur.component";
import {RechercheDepotComponent} from "./recherche-depot/recherche-depot.component";
import {RechercheSiteComponent} from "./recherche-site/recherche-site.component";
import {RechercheProduitComponent} from "./recherche-produit/recherche-produit.component";

const routes: Routes = [{
  path: '',
  component: RechercheComponent,
  children: [
    {
      path: 'user',
      component: RechercheUtilisateurComponent
    },
    {
      path: 'depot',
      component: RechercheDepotComponent
    },
    {
      path: 'contact',
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
  ]
}
]
export const RechercheRouter =RouterModule.forChild(routes)
