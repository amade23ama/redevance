import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegexConstantes } from 'src/app/core/constantes/regexConstantes';
import { ActionBtn } from 'src/app/core/interfaces/actionBtn';
import { AppConfigService } from 'src/app/core/services/app-config.service';
import { AuthService } from 'src/app/core/services/auth.service';
import { ModalService } from 'src/app/core/services/modal.service';

@Component({
  selector: 'forget-password',
  templateUrl: './forget-password.component.html',
  styleUrls: ['./forget-password.component.scss']
})
export class ForgetPasswordComponent {

 //déclaration des boutons d'action
  boutons: ActionBtn[] = [];

 // formulaire groupe de mdp oublié
 forgotPwdForm :FormGroup;
 userEmail: FormControl = new FormControl('', {validators: [Validators.required, Validators.email, Validators.pattern(RegexConstantes.REGEX_MAIL)]});

  constructor(public appConfig: AppConfigService, private router:Router, private modalService: ModalService,
      private formBuilder: FormBuilder, private authService :AuthService,) {
    this.forgotPwdForm = this.formBuilder.group({
        email: this.userEmail
        });
   }

  ngOnInit() {
  }

  /** réinitialisation de mdp  */
  resetPwd(){
    this.authService.reset(this.forgotPwdForm.value).subscribe((isOk) => {
        if (isOk) {
            this.forgotPwdForm.disable();
            this.modalService.ouvrirModalConfirmation(this.appConfig.getLabel('reset.password.confirmation.message'));
            this.router.navigate(["/login"]);
        }
    });
  }

  /** retour à la page d'authentification*/
  cancel(){
    this.router.navigate(["/login"]);
  }

  // getter d'acces rapide
  get emailForm(): AbstractControl { return this.forgotPwdForm?.get("email");}

}
