import {Component, Input, OnInit} from '@angular/core';
import {UtilisateurService} from "../../../core/services/utilisateur.service";
import {Utilisateur} from "../../../core/interfaces/utilisateur";
import {ActivatedRoute} from "@angular/router";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AppConfigService} from "../../../core/services/app-config.service";
import {Profil} from "../../../core/interfaces/profil";
import {ActionBtn} from "../../../core/interfaces/actionBtn";
import {Actions} from "../../../core/enum/actions";

@Component({
  selector: 'app-utilisateur',
  templateUrl: './utilisateur.component.html',
  styleUrls: ['./utilisateur.component.scss']
})
export class UtilisateurComponent implements OnInit{
  utilisateurCourant:Utilisateur
  prenom: FormControl=new FormControl();
  nom: FormControl=new FormControl();
  email : FormControl=new FormControl();
  telephone: FormControl = new FormControl( );
  active: FormControl = new FormControl(true);
  profils: FormControl = new FormControl()
  titre:string
  btns: ActionBtn[] = [];
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
        this.titre="Modification utilisateur"
        this.utilisateurService.getUtilisateurParId(params['contextInfo']).subscribe(()=>{
          this.utilisateurCourant=this.utilisateurService.getUtilisateurCourant();
          this.myform.patchValue(this.utilisateurCourant)
          this.initListbtns();
        })
      } else {
        this.titre="Creation utilisateur"
        this.initListbtns();
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
  reset(formToReset:any){
    this.myform.controls[formToReset]?.setValue('');
  }
  private initListbtns() {
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.enregistrer'),
      Actions.ENREGISTRER, this.isEnrgBtnDisplayed(), false, true, true, 'save'));
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.modifier'),
      Actions.MODIFIER, this.isModifBtnAffiche(), false, true, true, 'create'));
    return this.btns;
  }
  isEnrgBtnDisplayed(){
    this.utilisateurCourant = this.utilisateurService.getUtilisateurCourant();
    if(!this.utilisateurCourant?.id){
      return true
    }
    return false
  }
  isModifBtnAffiche(){
    this.utilisateurCourant = this.utilisateurService.getUtilisateurCourant();
    if(this.utilisateurCourant?.id){
      return true
    }
    return false
  }

  utilisateurAction(event: Actions){

  }
}
