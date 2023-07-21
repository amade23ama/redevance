import {NgModule} from "@angular/core";
import {LayoutComponent} from "../layout.component";
import {AutorisationDirective} from "../../core/directives/autorisation.directive";
import {layoutRouter} from "../layout.router";
import {MaterialModule} from "../../material.module";
import {NgIf, NgOptimizedImage} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import {SharedModule} from "../shared-Module/shared.module";
import {MatSidenavModule} from "@angular/material/sidenav";
import {RouterModule} from "@angular/router";
import {SessionTimerService} from "../../core/services/Session.timer.service";
import {DepotComponent} from "./depot.component";
import {DepotRouter} from "./depot.router";

@NgModule({
  declarations: [
    DepotComponent,
    AutorisationDirective
  ],
  imports: [
    DepotRouter,
    MaterialModule,
    NgOptimizedImage,
    ReactiveFormsModule,
    SharedModule,
    MatSidenavModule,
    MaterialModule,
    NgIf,
  ],
  exports: [RouterModule,AutorisationDirective],
  providers:[]
})
export class DepotModule {}
