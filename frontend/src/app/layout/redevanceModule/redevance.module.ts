import {NgModule} from "@angular/core";
import {SharedModule} from "../shared-Module/shared.module";
import { HomeCardComponent } from './home-card/home-card.component';
import { UserInfoComponent } from './user-info/user-info.component';
import {AsyncPipe, NgForOf, NgIf} from "@angular/common";
import {MaterialModule} from "../../material.module";
import {MatLegacyChipsModule} from "@angular/material/legacy-chips";
import {DcsomGrapheSimpleComponent} from "./dcsom-graphe/dcsom-graphe-simple/dcsom-graphe-simple.component";
import {NgApexchartsModule} from "ng-apexcharts";
import {DcsomGrapheBarComponent} from "./dcsom-graphe/dcsom-graphe-bar/dcsom-graphe-bar.component";

@NgModule({
  declarations: [
    HomeCardComponent,
    UserInfoComponent,
    DcsomGrapheSimpleComponent,
    DcsomGrapheBarComponent
  ],
  imports: [
    AsyncPipe,
    NgIf,
    MaterialModule,
    NgForOf,
    MatLegacyChipsModule,
    NgApexchartsModule
  ],
  exports: [
    HomeCardComponent,

  ],
  providers: [],
})
export class RedevanceModule {
}
