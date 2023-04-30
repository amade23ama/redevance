import {NgModule} from "@angular/core";
import { HomeComponent } from './home/home.component';
import { DisconnectedHomeComponent } from './home/disconnected-home/disconnected-home.component';
import { ConnectedHomeComponent } from './home/connected-home/connected-home.component';
import { PublicHomeComponent } from './home/public-home/public-home.component';
import { DscomFooterComponent } from './dscom-footer/dscom-footer.component';
import { DscomHeaderComponent } from './dscom-header/dscom-header.component';
import { DscomMessageComponent } from './dscom-message/dscom-message.component';
import { SpinnerComponent } from './spinner/spinner.component';
import { ConfirmationDialogComponent } from './dialog/confirmation-dialog/confirmation-dialog.component';
import { ActionBtnsComponent } from './action-btns/action-btns.component';
import {CommonModule, NgOptimizedImage} from "@angular/common";
import {MaterialModule} from "../material.module";
import { DscomLoginComponent } from './dscom-login/dscom-login.component';
import {FlexLayoutModule} from "@angular/flex-layout";
import {ReactiveFormsModule} from "@angular/forms";
import { DscomInfoComponent } from './dscom-info/dscom-info.component';

@NgModule({
  declarations: [
    HomeComponent,
    DisconnectedHomeComponent,
    ConnectedHomeComponent,
    PublicHomeComponent,
    DscomFooterComponent,
    DscomHeaderComponent,
    DscomMessageComponent,
    SpinnerComponent,
    ConfirmationDialogComponent,
    ActionBtnsComponent,
    DscomLoginComponent,
    DscomInfoComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    NgOptimizedImage,
    MaterialModule,
    ReactiveFormsModule
  ],
  exports: [
    DscomFooterComponent,
    SpinnerComponent,
    DscomHeaderComponent
  ],
  providers: [],
})
export class SharedModule {
}
