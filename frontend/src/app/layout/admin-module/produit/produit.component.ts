import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { MatDialog } from "@angular/material/dialog";
import { ActivatedRoute } from "@angular/router";
import { ModalService } from "src/app/core/services/modal.service";
import { UrlService } from "src/app/core/services/url.service";
import { Actions } from "../../../core/enum/actions";
import { ActionBtn } from "../../../core/interfaces/actionBtn";
import { openCloseTransition } from "../../../core/interfaces/open-close.transition";
import { Produit } from "../../../core/interfaces/produit";
import { AppConfigService } from "../../../core/services/app-config.service";
import { ProduitService } from "../../../core/services/produit.service";


@Component({
  selector: 'app-produit',
  templateUrl: './produit.component.html',
  styleUrls: ['./produit.component.scss'],
  animations: [openCloseTransition]
})
export class ProduitComponent implements OnInit{
  titre="Créer un nouveau  produit"
  id: FormControl = new FormControl()
  nomSRC: FormControl = new FormControl('',[Validators.required])
 // nomNORM: FormControl = new FormControl('',[Validators.required]);
  densiteGRM: FormControl = new FormControl('',[Validators.required]);
  densiteKGM: FormControl = new FormControl('',[Validators.required]);
  dateCreation: FormControl = new FormControl();
  dateModification:FormControl = new FormControl();
  myform: FormGroup = this.builder.group({
    id: this. id,
    nomSRC: this.nomSRC,
   // nomNORM: this.nomNORM,
    densiteGRM:this.densiteGRM,
    densiteKGM:this.densiteKGM,
    dateCreation: this.dateCreation,
    dateModification: this.dateModification,
  })
  btns: ActionBtn[] = [];
  produitCourant:Produit;

  // indique si on est en modification
  isModeModification = false;

  constructor(private builder: FormBuilder,public appConfig:AppConfigService,public produitService:ProduitService,
  private readonly activatedRoute: ActivatedRoute, public dialog: MatDialog,
  public modalService: ModalService, public urlService: UrlService) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams?.subscribe(async params => {
      if (params['contextInfo']) {
        this.isModeModification = true;
        this.titre="Modification Produit"
        this.produitService.getProduitParId(params['contextInfo']).subscribe(()=>{
          this.produitCourant=this.produitService.getProduitCourant()
          this.myform.patchValue(this.produitCourant)
          this.majBtnActive()
        })
      } else {
        this.isModeModification = false;
        this.titre="Création Produit";
        this.majBtnActive()
      }
      this.initListbtns();
    });
  }
  reset(formToReset:any){
    this.myform.controls[formToReset]?.setValue('');
  }

  private initListbtns() {
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.annuler'), Actions.ANNULER, true, false, false, true, 'keyboard_arrow_left'));
      this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.creer'), Actions.CREER, !this.isModeModification, true, true, true, 'save'));
      this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.modifier'), Actions.MODIFIER, this.isModeModification, true, true, true, 'create'));
    return this.btns;
  }

  // Si on a un id, alors on est en modification sinon en création
  isModifBtnDisplayed(){
    this.produitCourant = this.produitService.getProduitCourant()
    return this.produitCourant?.id ? true : false;
  }

  // Si on a un id, alors on est en modification sinon en création
  isCreateBtnDisplayed(){
    return !this.produitCourant?.id ? true : false;
  }

  /** Action sur les boutons Enregistrer ou ANNULER */
  produitAction(event: Actions){
    //Le click sur le bouton ENREGISTRER
    if (event === Actions.CREER || event === Actions.MODIFIER) {
      const b= this.myform.value;
      this.produitService.enregistrerProduit(this.myform.value).subscribe()
    }

    //Le click sur le bouton Annuler
    if (event === Actions.ANNULER) {
      this.modalService.ouvrirModaleAnnulation(this.urlService.getPreviousUrl(), this.isModeModification ? 'modification de produit' : 'création de produit'); //Ouverture de la modale d'annulation
    }
  }

  // Activation et désactivation des boutons en fonction des actions de l'utilisateur
  majBtnActive(){
    // Formulaire non valid
    this.myform?.valueChanges.subscribe((res)=>{
      if(this.myform.invalid){
          if (this.isModeModification) {
            this.majBtnState(Actions.CREER, true, false);
            this.majBtnState(Actions.MODIFIER, true, true);
          }else{
            this.majBtnState(Actions.CREER, true, true);
            this.majBtnState(Actions.MODIFIER, true, false);
          }
      }

      // Formulaire valid
      if(!this.myform.invalid){
          if (this.isModeModification) {
            this.majBtnState(Actions.CREER, true, false);
            this.majBtnState(Actions.MODIFIER, false, true);
          }else{
            this.majBtnState(Actions.CREER, false, true);
            this.majBtnState(Actions.MODIFIER, true, false);
          }
      }
    })

  }

    /** ouvrir Modale Annulation */
    majBtnState(a: Actions, disabled: boolean, display: boolean) {
      this.btns.forEach(b => {
        if (b.id === a) {
          b.disabled = disabled;
          b.display = display;
        }
      });
    }
}
