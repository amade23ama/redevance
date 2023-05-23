import {Component, Input, OnInit} from '@angular/core';
import {UtilisateurService} from "../../../core/services/utilisateur.service";
import {Utilisateur} from "../../../core/interfaces/utilisateur";
import {ActivatedRoute} from "@angular/router";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AppConfigService} from "../../../core/services/app-config.service";
import {Profil} from "../../../core/interfaces/profil";

@Component({
  selector: 'app-utilisateur',
  templateUrl: './utilisateur.component.html',
  styleUrls: ['./utilisateur.component.scss']
})
export class UtilisateurComponent implements OnInit{
  utilisateur:Utilisateur
  prenom: FormControl=new FormControl();
  nom: FormControl=new FormControl();
  email : FormControl=new FormControl();
  telephone: FormControl = new FormControl( );
  active: FormControl = new FormControl(true);
  profils: FormControl = new FormControl()
  constructor(public utilisateurService:UtilisateurService,private readonly activatedRoute: ActivatedRoute,
              public builder:FormBuilder,public appConfig: AppConfigService,) {
  }
  myform:FormGroup= this.builder.group({
    prenom:this.prenom,
    nom:this.nom,
    email:this.email,
    telephone:this.telephone,
    active: this.active,
    profils: this.profils
  });
  ngOnInit(): void {

    this.activatedRoute.queryParams?.subscribe(async params => {
      if (params['contextInfo']) {
        this.utilisateurService.getUtilisateurParId(params['contextInfo']).subscribe(()=>{
          this.utilisateur=this.utilisateurService.getUtilisateurCourant();
          this.myform.patchValue(this.utilisateur)
        })
      } else {

      }
    });

  }
  sauvegarder() {
    const  val = this.myform.value
    console.log(" valeur ")
    /*this.utilisateurService.sauvegarder(this.myform.value).subscribe(()=>{
      this.router.navigateByUrl("recherche/user")
    })
    */
  }
}
