import {CommonModule, NgIf, NgOptimizedImage} from "@angular/common";
import {layoutRouter} from "./layout.router";
import {NgModule} from "@angular/core";
import { LayoutComponent } from './layout.component';
import {SharedModule} from "./shared-Module/shared.module";
import {MaterialModule} from "../material.module";
import {ReactiveFormsModule} from "@angular/forms";
import {MatSidenavModule} from "@angular/material/sidenav";
import {RouterModule} from "@angular/router";
import {MatExpansionModule} from "@angular/material/expansion";
import {AdminModule} from "./admin-module/admin.module";
import {AppModule} from "../app.module";
import {AutorisationDirective} from "../core/directives/autorisation.directive";
import {SessionTimerService} from "../core/services/Session.timer.service";

@NgModule({
  declarations: [
    LayoutComponent,
    AutorisationDirective
  ],
    imports: [
        layoutRouter,
        MaterialModule,
        NgOptimizedImage,
        ReactiveFormsModule,
        SharedModule,
        MatSidenavModule,
        MaterialModule,
        NgIf,
    ],
  exports: [RouterModule,AutorisationDirective],
  providers:[SessionTimerService]
})
export class LayoutModule {}
