import { RouterModule, Routes } from "@angular/router";
import { AdminComponent } from "./admin.component";
import { ChargementComponent } from "./chargement/chargement.component";
import { ClasseVoitureComponent } from "./classe-voiture/classe-voiture.component";
import { ExploitationComponent } from "./exploitation/exploitation.component";
import { ProduitComponent } from "./produit/produit.component";
import { SiteComponent } from "./site/site.component";
import { UpdatePwdComponent } from "./utilisateur/update-pwd/update-pwd.component";
import { UtilisateurCreateComponent } from "./utilisateur/utilisateur-create/utilisateur-create.component";
import { UtilisateurComponent } from "./utilisateur/utilisateur.component";
import { VehiculeComponent } from "./vehicule/vehicule.component";

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
            path: 'categorie',
            component: ClasseVoitureComponent
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
          {
            path: 'user/update',
            component: UpdatePwdComponent
          },
          {
            path: 'chargement',
            component: ChargementComponent
          }

  ]
}]

export const AdminRouter = RouterModule.forChild(routes)
