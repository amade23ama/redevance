import {CommonModule, NgIf, NgOptimizedImage} from "@angular/common";
import {layoutRouter} from "./layout.router";
import {NgModule} from "@angular/core";
import { LayoutComponent } from './layout.component';
import {SharedModule} from "./shared-Module/shared.module";
import {MaterialModule} from "../material.module";
import {ReactiveFormsModule} from "@angular/forms";
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatSidenavModule} from "@angular/material/sidenav";
import {AdminModule} from "./admin-module/admin.module";
import {RedevanceModule} from "./redevanceModule/redevance.module";

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
        AdminModule,
        NgIf,
    ]
})
export class LayoutModule {}
