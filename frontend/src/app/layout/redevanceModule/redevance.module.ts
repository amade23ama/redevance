import {NgModule} from "@angular/core";
import {SharedModule} from "../shared-Module/shared.module";
import { HomeCardComponent } from './home-card/home-card.component';
import { UserInfoComponent } from './user-info/user-info.component';

@NgModule({
  declarations: [
    HomeCardComponent,
    UserInfoComponent
  ],
  imports: [
  ],
  exports: [
    HomeCardComponent
  ],
  providers: [],
})
export class RedevanceModule {
}
