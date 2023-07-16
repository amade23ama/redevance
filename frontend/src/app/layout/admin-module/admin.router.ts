import {RouterModule, Routes} from "@angular/router";
import {LayoutComponent} from "../layout.component";
import {DscomMessageComponent} from "../shared-Module/dscom-message/dscom-message.component";
import {HomeComponent} from "../shared-Module/home/home.component";
import {UtilisateurCreateComponent} from "./utilisateur/utilisateur-create/utilisateur-create.component";
import {SiteComponent} from "./site/site.component";
import {AdminComponent} from "./admin.component";
import {TransporteurComponent} from "./transporteur/transporteur.component";
import {StepperComponent} from "./vehicule/stepper/stepper.component";
import {VehiculeComponent} from "./vehicule/vehicule.component";
import {UtilisateurComponent} from "./utilisateur/utilisateur.component";
import {ProduitComponent} from "./produit/produit.component";

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
            path: 'transpoteur',
            component: TransporteurComponent
          },
          {
            path: 'vehiculeNouveau',
            component: StepperComponent
          },
          {
            path: 'utilisateur',
            component: UtilisateurComponent
          },
          {
            path: 'produit',
            component: ProduitComponent
          },

  ]
}]

export const AdminRouter = RouterModule.forChild(routes)
