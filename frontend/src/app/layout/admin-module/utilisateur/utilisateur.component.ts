import {Component, Input, OnInit} from '@angular/core';
import {UtilisateurService} from "../../../core/services/utilisateur.service";
import {Utilisateur} from "../../../core/interfaces/utilisateur";
import {ActivatedRoute} from "@angular/router";
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AppConfigService} from "../../../core/services/app-config.service";
import {Profil} from "../../../core/interfaces/profil";
import {ActionBtn} from "../../../core/interfaces/actionBtn";
import {Actions} from "../../../core/enum/actions";
import {AuthService} from "../../../core/services/auth.service";
import {DroitEnum} from "../../../core/enum/droit-enum";

@Component({
  selector: 'app-utilisateur',
  templateUrl: './utilisateur.component.html',
  styleUrls: ['./utilisateur.component.scss']
})
export class UtilisateurComponent implements OnInit{
  utilisateurCourant:Utilisateur
  id: FormControl = new FormControl();
  prenom: FormControl=new FormControl('',[Validators.required, Validators.minLength(3)]);
  nom: FormControl=new FormControl('',[Validators.required, Validators.minLength(2)]);
  email : FormControl=new FormControl('', {
    validators: [Validators.required, Validators.email],
    asyncValidators: [this.checkEmail.bind(this)],
    updateOn: 'blur'
  });
  telephone: FormControl = new FormControl( );
  active: FormControl = new FormControl();
  profils: FormControl = new FormControl('',[Validators.required])
  titre:string
  btns: ActionBtn[] = [];
  myform :FormGroup
  isUpdate:boolean
  constructor(public utilisateurService:UtilisateurService,private readonly activatedRoute: ActivatedRoute,
              public builder:FormBuilder,public appConfig: AppConfigService,
              private readonly authService: AuthService) {
    this.myform= this.builder.group({
      id:this.id,
      prenom:this.prenom,
      nom:this.nom,
      email:this.email,
      telephone:this.telephone,
      active: this.active,
      profils: this.profils
    });
  }

  ngOnInit(): void {

    this.activatedRoute.queryParams?.subscribe(async params => {
      if (params['contextInfo']) {
        this.titre="Modification utilisateur"
        this.utilisateurService.getUtilisateurParId(params['contextInfo']).subscribe(()=>{
          this.utilisateurCourant=this.utilisateurService.getUtilisateurCourant();
          this.myform.patchValue(this.utilisateurCourant)
          this.initListbtns();
          this.isUpdate=true
          this.majBtnActive()
          this.droit()
        })
      } else {
        this.titre="Creation utilisateur"
        this.initListbtns();
        this.isUpdate=false
        this.majBtnActive()
        this.droit()
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
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.annuler'),
      Actions.ANNULER, true, false, true, true, 'keyboard_arrow_left'));
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.enregistrer'),
      Actions.ENREGISTRER, this.isEnrgBtnDisplayed(), true, true, true, 'save'));
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.modifier'),
      Actions.MODIFIER, this.isModifBtnAffiche(), true, true, true, 'create'));
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
    if (event === Actions.ENREGISTRER) {
    const b= this.myform.value;
      this.utilisateurService.enregistrer(this.myform.value).subscribe()
    }
    if (event === Actions.MODIFIER) {
      const b= this.myform.value;
      this.utilisateurService.enregistrer(this.myform.value).subscribe()
    }
  }
  checkEmail(control:AbstractControl){
    return this.utilisateurService.emailCheck(control,this.utilisateurCourant.id)
  }

  /*majBtnState(a: Actions, displayed: boolean) {
    this.btns.forEach(b => {
      if (b.id === a) {
        b.display = displayed;
      }
    });
    */
 majBtnActive(){
   this.myform?.valueChanges.subscribe((res)=>{
     if(this.myform.invalid){
       this.btns.forEach(b=>{
         b.disabled=true
       });
     }else{
       this.btns.forEach(b=>{
         b.disabled=false
       });
       this.droit()
     }
   })
   if(!this.myform.invalid){
     this.btns.forEach(b=>{
       b.disabled=false
     });
   }
    }
    droit(){
      if(this.authService.hasDroits(DroitEnum.CONSULT)){
        this.myform.disable()
        this.btns.forEach(b=>{b.disabled=true});
      }
    }
  }
