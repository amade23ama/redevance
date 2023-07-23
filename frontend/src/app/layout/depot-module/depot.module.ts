import {NgModule} from "@angular/core";
import {LayoutComponent} from "../layout.component";
import {AutorisationDirective} from "../../core/directives/autorisation.directive";
import {layoutRouter} from "../layout.router";
import {MaterialModule} from "../../material.module";
import {CommonModule, NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {SharedModule} from "../shared-Module/shared.module";
import {MatSidenavModule} from "@angular/material/sidenav";
import {RouterModule, RouterOutlet} from "@angular/router";
import {SessionTimerService} from "../../core/services/Session.timer.service";
import {DepotComponent} from "./depot.component";
import {DepotRouter} from "./depot.router";
import {DepotCreationComponent} from "./depot-creation/depot-creation.component";
import {MatTableModule} from "@angular/material/table";
import {MatIconModule} from "@angular/material/icon";
import {LayoutModule} from "../layout.module";
import {UserModule} from "../admin-module/utilisateur/utilisateur.module";
import {NgxMatSelectSearchModule} from "ngx-mat-select-search";
import {DepotModificationComponent} from "./depot-modification/depot-modification.component";
import {NgxFileDragDropModule} from "ngx-file-drag-drop";


@NgModule({
  declarations: [
    DepotComponent,
    DepotCreationComponent,
    DepotModificationComponent
  ],
  imports: [
    DepotRouter,
    CommonModule,
    MaterialModule,
    UserModule,
    FormsModule,
    ReactiveFormsModule,
    RouterOutlet,
    NgxMatSelectSearchModule,
    SharedModule,
    NgxFileDragDropModule
  ],
  exports: [],
  providers:[]
})
export class DepotModule {}
