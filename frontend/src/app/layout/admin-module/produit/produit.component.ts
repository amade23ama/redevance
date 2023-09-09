import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { MatDialog } from "@angular/material/dialog";
import { ActivatedRoute } from "@angular/router";
import { AnnulationModaleComponent } from "src/app/core/modals/annulation-modale/annulation-modale.component";
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
  produitCourant:Produit;
  constructor(private builder: FormBuilder,public appConfig:AppConfigService,public produitService:ProduitService,
  private readonly activatedRoute: ActivatedRoute, public dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.activatedRoute.queryParams?.subscribe(async params => {
      this.initListbtns();
      if (params['contextInfo']) {
        this.titre="Modification Produit"
        this.produitService.getProduitParId(params['contextInfo']).subscribe(()=>{
          this.produitCourant=this.produitService.getProduitCourant()
          this.myform.patchValue(this.produitCourant)
          this.majBtnActive()
        })
      } else {
        this.titre="Création Produit";
        this.majBtnActive()
      }
    });
  }
  reset(formToReset:any){
    this.myform.controls[formToReset]?.setValue('');
  }

  private initListbtns() {
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.annuler'),
      Actions.ANNULER, true, false, true, true, 'keyboard_arrow_left'));
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.enregistrer'),
      Actions.ENREGISTRER, this.isEnrgBtnDisplayed(), true, true, true, 'save'));
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

  /** Action sur les boutons Enregistrer ou ANNULER */
  produitAction(event: Actions){
    //Le click sur le bouton ENREGISTRER
    if (event === Actions.ENREGISTRER) {
      const b= this.myform.value;
      this.produitService.enregistrerProduit(this.myform.value).subscribe()
    }

    //Le click sur le bouton Annuler
    if (event === Actions.ANNULER) {
      this.ouvrirModaleAnnulation('0ms', '0ms'); //Ouverture de la modale d'annulation
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

    /** ouvrir Modale Annulation */
    ouvrirModaleAnnulation(enterAnimationDuration: string, exitAnimationDuration: string): void {
      this.dialog.open(AnnulationModaleComponent, {
        width: '500px',
        data: {url: '/recherche/produit'},
        enterAnimationDuration,
        exitAnimationDuration,
      });
    }
}
