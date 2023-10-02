import { NgIf, NgOptimizedImage } from "@angular/common";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { MatSidenavModule } from "@angular/material/sidenav";
import { RouterModule } from "@angular/router";
import { SessionTimerService } from "../core/services/Session.timer.service";
import { MaterialModule } from "../material.module";
import { LayoutComponent } from './layout.component';
import { layoutRouter } from "./layout.router";
import { SharedModule } from "./shared-Module/shared.module";

@NgModule({
  declarations: [
    LayoutComponent
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
  exports: [RouterModule],
  providers:[SessionTimerService]
})
export class LayoutModule {}
