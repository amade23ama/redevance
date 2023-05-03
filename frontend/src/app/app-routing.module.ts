import { NgModule } from '@angular/core';
import {ExtraOptions, provideRouter, RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./layout/shared-Module/home/home.component";
import {DisconnectedHomeComponent} from "./layout/shared-Module/home/disconnected-home/disconnected-home.component";
import {LoginComponent} from "./login/login.component";
import {RouteGuard} from "./core/guards/route-guard.service";


/**
 * listing des routes public accessible en mode non connecté
 */
const routesPublic: Routes = [
  {path: 'login', component: LoginComponent},
  {
    path: '',loadChildren: () => import('./layout/layout.module')
      .then(modules => modules.LayoutModule),canActivate:[RouteGuard]
  }

  //{path: '', component: HomeComponent},
  //{path: 'home', component: HomeComponent},
  //{path: 'user-info', component: UserInfoComponent},
  //{path: 'deconnexion', component: DisconnectedHomeComponent},
];

/**
 * listing des routes privé accessibles uniquement lorsque l'utilisateur est connecté
 */
const routesPrivate: Routes = [
  //{path: 'contrat', component: ContratComponent, canActivate: [RouteGuard]},
  //{path: 'recherche', component: RechercheComponent, canActivate: [RouteGuard]}
];

/**
 * groupement des routes public et private
 */
export const routes: Routes = [
  ...routesPublic,
  ...routesPrivate,
  //{path: '**', component: HomeComponent}
];

const routerOptions: ExtraOptions = {
  useHash: true,
  scrollPositionRestoration: 'enabled' // restauration de la position de défilement lors de la navigation

};

//const routes: Routes = [];
@NgModule({
  imports: [
    RouterModule.forRoot(routes,routerOptions)

  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }

