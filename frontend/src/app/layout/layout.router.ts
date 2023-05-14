import {RouterModule, Routes} from "@angular/router";
import {LayoutComponent} from "./layout.component";
import {HomeComponent} from "./shared-Module/home/home.component";


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
    {
      path: 'recherche',
      loadChildren: () => import('./recherche-module/recherche.module').then(m => m.RechercheModule)
    },
  ]
}]

export const layoutRouter = RouterModule.forChild(routes)

