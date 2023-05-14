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
  exports: [RouterModule]
})
export class LayoutModule {}
