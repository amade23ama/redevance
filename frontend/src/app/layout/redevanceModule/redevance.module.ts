import {NgModule} from "@angular/core";
import { HomeCardComponent } from './home-card/home-card.component';
import { UserInfoComponent } from './user-info/user-info.component';
import {AsyncPipe, NgForOf, NgIf} from "@angular/common";
import {MaterialModule} from "../../material.module";
import {MatLegacyChipsModule} from "@angular/material/legacy-chips";
import {NgApexchartsModule} from "ng-apexcharts";
import {DcsomGrapheBarComponent} from "./dcsom-graphe/dcsom-graphe-bar/dcsom-graphe-bar.component";
import {NgxChartsModule} from "@swimlane/ngx-charts";
import {DcsomGrapheCercleComponent} from "./dcsom-graphe/dcsom-graphe-cercle/dcsom-graphe-cercle.component";
import {DcsomGrapheBarSimpleComponent} from "./dcsom-graphe/dcsom-graphe-bar-simple/dcsom-graphe-bar-simple.component";
import {ReactiveFormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    HomeCardComponent,
    UserInfoComponent,
    DcsomGrapheBarComponent,
    DcsomGrapheCercleComponent,
    DcsomGrapheBarSimpleComponent
  ],
  imports: [
    AsyncPipe,
    NgIf,
    MaterialModule,
    NgForOf,
    MatLegacyChipsModule,
    NgApexchartsModule,
    NgxChartsModule,
    ReactiveFormsModule,
  ],
  exports: [
    HomeCardComponent,

  ],
  providers: [],
})
export class RedevanceModule {
}
