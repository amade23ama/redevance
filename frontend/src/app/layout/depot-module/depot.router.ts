import {RouterModule, Routes} from "@angular/router";
import {DepotComponent} from "./depot.component";

const routes: Routes = [{
  path: '',
  component: DepotComponent,
  children: [
    {
      path: 'depot',
      component: DepotComponent
    },

  ]
}]

export const DepotRouter = RouterModule.forChild(routes)
