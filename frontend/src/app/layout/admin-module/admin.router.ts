import {RouterModule, Routes} from "@angular/router";
import {LayoutComponent} from "../layout.component";
import {DscomMessageComponent} from "../shared-Module/dscom-message/dscom-message.component";
import {HomeComponent} from "../shared-Module/home/home.component";
import {UtilisateurUpdateComponent} from "./utilisateur-update/utilisateur-update.component";
import {SiteComponent} from "./site/site.component";

const routes: Routes = [{
  path: '',
  component: UtilisateurUpdateComponent,
  children: [
    {
      path: '',
      component: UtilisateurUpdateComponent
    },
    {
      path: 'site',
      component: SiteComponent
    }
  ]
}]

export const AdminRouter = RouterModule.forChild(routes)
