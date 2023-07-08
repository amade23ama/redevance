import {AfterViewInit, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AppConfigService} from "../../../core/services/app-config.service";
import {Utilisateur} from "../../../core/interfaces/utilisateur";
import {ParamService} from "../../../core/services/param.service";
import {UtilisateurService} from "../../../core/services/utilisateur.service";

@Component({
  selector: 'app-formulaire-utilisateur',
  templateUrl: './formulaire-utilisateur.component.html',
  styleUrls: ['./formulaire-utilisateur.component.scss']
})
export class FormulaireUtilisateurComponent implements OnInit{
  profils$ = this.paramService.profils$

  @Input() id: FormControl
  @Input() prenom: FormControl
  @Input() nom: FormControl
  @Input() email: FormControl
  @Input() telephone: FormControl
  @Input() active: FormControl
  @Input() profils: FormControl
  @Input() userForm: FormGroup = this.builder.group({})
  @Output() btnClear:EventEmitter<any> =new EventEmitter<any>();
  @Input() titre: string
  @Input() update:boolean;
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
  reset(formToReset:any) {
    this.btnClear.emit(formToReset)
  }

  getStatus() {
    return this.labelActive = this.statusActive == true ? "activé" : "désactivé";
  }

}
