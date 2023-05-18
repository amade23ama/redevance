import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ValidationErrors, Validators} from "@angular/forms";
import {ParamService} from "../../../../core/services/param.service";
import {AppConfigService} from "../../../../core/services/app-config.service";
import {UtilisateurService} from "../../../../core/services/utilisateur.service";

@Component({
  selector: 'app-utilisateur-update',
  templateUrl: './utilisateur-create.component.html',
  styleUrls: ['./utilisateur-create.component.scss']
})
export class UtilisateurCreateComponent implements OnInit {
  id: FormControl = new FormControl()
  prenom: FormControl = new FormControl('', [Validators.required, Validators.minLength(3)])
  nom: FormControl = new FormControl('', [Validators.required, Validators.minLength(2)])
  email: FormControl = new FormControl('',
    {
      validators: [Validators.required, Validators.email],
      asyncValidators: [this.utilisateurService.checkEmail.bind(this.utilisateurService)],
      updateOn: 'blur'
    });
  //login: FormControl = new FormControl('');
 // password: FormControl = new FormControl('', [Validators.required, Validators.minLength(6), Validators.maxLength(10)]);
  telephone: FormControl = new FormControl('', [Validators.required]);
  dateCreation: FormControl = new FormControl();
  dateModification: FormControl = new FormControl();
  active: FormControl = new FormControl(true);
  profils: FormControl = new FormControl('', [Validators.required])

  myform: FormGroup = this.builder.group({
    id: this.id,
    prenom: this.prenom,
    nom: this.nom,
    email: this.email,
    //login: this.login,
   // password: this.password,
    telephone: this.telephone,
    active: this.active,
    profils: this.profils,
    dateCreation: this.dateCreation,
    dateModification: this.dateModification
  })
  statusActive: boolean;
  hide: boolean = true;
  labelActive = "";
  profils$ = this.paramService.profils$

  constructor(private builder: FormBuilder, private paramService: ParamService,
              public appConfig: AppConfigService, public utilisateurService: UtilisateurService) {
  }

  ngOnInit(): void {
    this.paramService.chargementProfils().subscribe()
    this.active.valueChanges.subscribe((res) => {
      this.statusActive = res
    })
  }

  reset(formToReset: string) {
    this.myform.controls[formToReset]?.setValue('');
  }

  getStatus() {
    return this.labelActive = this.statusActive == true ? "activé" : "désactivé";
  }

  sauvegarder() {
    this.utilisateurService.sauvegarder(this.myform.value).subscribe()
    console.log(" save")
  }

  annuler() {
    console.log(" annuller")
  }


}
