import {Component, OnInit} from "@angular/core";
import {openCloseTransition} from "../../../core/interfaces/open-close.transition";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AppConfigService} from "../../../core/services/app-config.service";
import {ActionBtn} from "../../../core/interfaces/actionBtn";
import {Actions} from "../../../core/enum/actions";
import {ProduitService} from "../../../core/services/produit.service";


@Component({
  selector: 'app-produit',
  templateUrl: './produit.component.html',
  styleUrls: ['./produit.component.scss'],
  animations: [openCloseTransition]
})
export class ProduitComponent implements OnInit{
  titre="Creer un Nouveau  Produit"
  id: FormControl = new FormControl()
  nomSRC: FormControl = new FormControl('',[Validators.required])
  nomNORM: FormControl = new FormControl('',[Validators.required]);
  densiteGRM: FormControl = new FormControl('',[Validators.required]);
  densiteKGM: FormControl = new FormControl('',[Validators.required]);
  dateCreation: FormControl = new FormControl();
  dateModification:FormControl = new FormControl();
  myform: FormGroup = this.builder.group({
    id: this. id,
    nomSRC: this.nomSRC,
    nomNORM: this.nomNORM,
    densiteGRM:this.densiteGRM,
    densiteKGM:this.densiteKGM,
    dateCreation: this.dateCreation,
    dateModification: this.dateModification,
  })
  btns: ActionBtn[] = [];
  constructor(private builder: FormBuilder,public appConfig:AppConfigService,public produitService:ProduitService) {
  }

  ngOnInit(): void {
    this.initListbtns()
    this.majBtnActive()
  }
  reset(formToReset:any){
    this.myform.controls[formToReset]?.setValue('');
  }

  private initListbtns() {
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.enregistrer'),
      Actions.ENREGISTRER, this.isEnrgBtnDisplayed(), true, true, true, 'save'));
   /* this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.modifier'),
      Actions.MODIFIER, this.isModifBtnAffiche(), true, true, true, 'create'));
      *
    */
    return this.btns;
  }
  isEnrgBtnDisplayed(){
    return true
   /* this.utilisateurCourant = this.utilisateurService.getUtilisateurCourant();
    if(!this.utilisateurCourant?.id){
      return true
    }*/
    //return false
  }
  isModifBtnAffiche(){
    /*this.utilisateurCourant = this.utilisateurService.getUtilisateurCourant();
    if(this.utilisateurCourant?.id){
      return true
    }*/
    return false
  }

  utilisateurAction(event: Actions){
    if (event === Actions.ENREGISTRER) {
      const b= this.myform.value;
      this.produitService.enregistrerProduit(this.myform.value).subscribe()
    }
  }
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
      }
    })
    if(!this.myform.invalid){
      this.btns.forEach(b=>{
        b.disabled=false
      });
    }
  }
}
