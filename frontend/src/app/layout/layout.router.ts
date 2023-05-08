import {RouterModule, Routes} from "@angular/router";
import {LayoutComponent} from "./layout.component";
import {DscomMessageComponent} from "./shared-Module/dscom-message/dscom-message.component";
import {HomeComponent} from "./shared-Module/home/home.component";
import {RouteGuard} from "../core/guards/route-guard.service";
import {UtilisateurUpdateComponent} from "./admin-module/utilisateur/utilisateur-update/utilisateur-update.component";
import {SiteComponent} from "./admin-module/site/site.component";
import {VehiculeComponent} from "./admin-module/vehicule/vehicule.component";
import {TransporteurComponent} from "./admin-module/transporteur/transporteur.component";

const routes: Routes = [{
  path: '',
  component: LayoutComponent,
  children: [
    {
      path: '',
      component: HomeComponent
    },
    {
      path: 'test',
      component: HomeComponent
    },
    {
      path: 'admin',
      component: UtilisateurUpdateComponent
    },
    {
      path: 'admin/site',
      component: SiteComponent
    },
    {
      path: 'admin/vehicule',
      component: VehiculeComponent
    },
    {
      path: 'admin/transpoteur',
      component: TransporteurComponent
    },
  ]
}]

export const layoutRouter = RouterModule.forChild(routes)
