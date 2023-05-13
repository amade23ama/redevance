import {RouterModule, Routes} from "@angular/router";
import {LayoutComponent} from "./layout.component";
import {DscomMessageComponent} from "./shared-Module/dscom-message/dscom-message.component";
import {HomeComponent} from "./shared-Module/home/home.component";
import {RouteGuard} from "../core/guards/route-guard.service";
import {UtilisateurUpdateComponent} from "./admin-module/utilisateur/utilisateur-update/utilisateur-update.component";
import {SiteComponent} from "./admin-module/site/site.component";
import {VehiculeComponent} from "./admin-module/vehicule/vehicule.component";
import {TransporteurComponent} from "./admin-module/transporteur/transporteur.component";
import {StepperComponent} from "./admin-module/vehicule/stepper/stepper.component";
import {AdminComponent} from "./admin-module/admin.component";

const routes: Routes = [{
  path: '',
  component: LayoutComponent,
  children: [
    { path: '',
    component: HomeComponent
    },
  {
    path: 'admin',
    loadChildren: () => import('./admin-module/admin.module').then(m => m.AdminModule)
  },
  ]
}]

export const layoutRouter = RouterModule.forChild(routes)

