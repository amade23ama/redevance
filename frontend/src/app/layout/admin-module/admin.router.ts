import {RouterModule, Routes} from "@angular/router";
import {LayoutComponent} from "../layout.component";
import {DscomMessageComponent} from "../shared-Module/dscom-message/dscom-message.component";
import {HomeComponent} from "../shared-Module/home/home.component";
import {UtilisateurUpdateComponent} from "./utilisateur/utilisateur-update/utilisateur-update.component";
import {SiteComponent} from "./site/site.component";
import {AdminComponent} from "./admin.component";
import {TransporteurComponent} from "./transporteur/transporteur.component";
import {StepperComponent} from "./vehicule/stepper/stepper.component";
import {VehiculeComponent} from "./vehicule/vehicule.component";

const routes: Routes = [{
  path: '',
  component: AdminComponent,
  children: [
          {
            path: 'user',
            component: UtilisateurUpdateComponent
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

  ]
}]

export const AdminRouter = RouterModule.forChild(routes)
