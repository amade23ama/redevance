import {RouterModule, Routes} from "@angular/router";
import {UtilisateurCreateComponent} from "./utilisateur/utilisateur-create/utilisateur-create.component";
import {SiteComponent} from "./site/site.component";
import {AdminComponent} from "./admin.component";
import {VehiculeComponent} from "./vehicule/vehicule.component";
import {UtilisateurComponent} from "./utilisateur/utilisateur.component";
import {ProduitComponent} from "./produit/produit.component";
import {ExploitationComponent} from "./exploitation/exploitation.component";

const routes: Routes = [{
  path: '',
  component: AdminComponent,
  children: [
          {
            path: 'user',
            component: UtilisateurCreateComponent
          },
          {
            path: 'site',
            component: SiteComponent
          },
          {
            path: 'vehicule',
            component: VehiculeComponent
          },
          {
            path: 'utilisateur',
            component: UtilisateurComponent
          },
          {
            path: 'produit',
            component: ProduitComponent
          },
          {
            path: 'exploitation',
            component: ExploitationComponent
          },


  ]
}]

export const AdminRouter = RouterModule.forChild(routes)
