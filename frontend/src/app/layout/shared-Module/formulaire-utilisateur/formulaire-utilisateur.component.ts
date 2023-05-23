import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AppConfigService} from "../../../core/services/app-config.service";
import {Utilisateur} from "../../../core/interfaces/utilisateur";
import {ParamService} from "../../../core/services/param.service";

@Component({
  selector: 'app-formulaire-utilisateur',
  templateUrl: './formulaire-utilisateur.component.html',
  styleUrls: ['./formulaire-utilisateur.component.scss']
})
export class FormulaireUtilisateurComponent implements OnInit{
  profils$ = this.paramService.profils$

  @Input() id: FormControl =new FormControl();
  @Input() prenom: FormControl =new FormControl();
  @Input() nom: FormControl =new FormControl();
  @Input() email: FormControl =new FormControl();
  @Input() telephone: FormControl =new FormControl();
  @Input() active: FormControl =new FormControl(true);
  @Input() profils: FormControl =new FormControl();
  @Input() myform: FormGroup   = this.builder.group({})

  statusActive: boolean;
  hide: boolean = true;
  labelActive = "";
  constructor(private builder: FormBuilder,public appConfig: AppConfigService,private paramService: ParamService) {
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
}
