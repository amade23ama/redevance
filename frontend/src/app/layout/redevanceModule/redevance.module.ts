import {NgModule} from "@angular/core";
import {SharedModule} from "../shared-Module/shared.module";
import { HomeCardComponent } from './home-card/home-card.component';
import { UserInfoComponent } from './user-info/user-info.component';
import {AsyncPipe, NgForOf, NgIf} from "@angular/common";
import {MaterialModule} from "../../material.module";
import {MatLegacyChipsModule} from "@angular/material/legacy-chips";

@NgModule({
  declarations: [
    HomeCardComponent,
    UserInfoComponent
  ],
  imports: [

    SharedModule,
    MaterialModule,
    AsyncPipe
  ],
  exports: [
    HomeCardComponent
  ],
  providers: [],
})
export class RedevanceModule {
}
