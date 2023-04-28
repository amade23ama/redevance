import { NgModule } from '@angular/core';
import {ExtraOptions, RouterModule, Routes} from '@angular/router';


/**
 * listing des routes public accessible en mode non connecté
 */
const routesPublic: Routes = [
  ///{path: '', component: HomeComponent},
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
  imports: [RouterModule.forRoot(routes,routerOptions)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
