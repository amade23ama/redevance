import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from "@angular/router";
import { ModalService } from 'src/app/core/services/modal.service';
import { UrlService } from 'src/app/core/services/url.service';
import { Actions } from "../../../core/enum/actions";
import { ActionBtn } from "../../../core/interfaces/actionBtn";
import { openCloseTransition } from "../../../core/interfaces/open-close.transition";
import { Site } from "../../../core/interfaces/site";
import { AppConfigService } from "../../../core/services/app-config.service";
import { ParamService } from "../../../core/services/param.service";
import { SiteService } from "../../../core/services/site.service";



@Component({
  selector: 'app-site',
  templateUrl: './site.component.html',
  styleUrls: ['./site.component.scss'],
  animations: [openCloseTransition]
})
export class SiteComponent implements OnInit {
  titre="Créer un nouveau site"
  show = false;
  id: FormControl = new FormControl()
  nom: FormControl = new FormControl('',[Validators.required])
  localite: FormControl = new FormControl('',[Validators.required]);
  dateCreation: FormControl = new FormControl();
  dateModification:FormControl = new FormControl();
  myform: FormGroup = this.builder.group({
    id: this. id,
    nom: this.nom,
    localite: this.localite,
    dateCreation: this.dateCreation,
    dateModification: this.dateModification,
  })

  btns: ActionBtn[] = [];
  siteCourant:Site;

  // indique si on est en modification
  isModeModification = false;

  constructor(private builder: FormBuilder, public dialog: MatDialog,
              public appConfig:AppConfigService,private paramService: ParamService,
              public siteService:SiteService, private readonly activatedRoute: ActivatedRoute,
              public modalService: ModalService, public urlService: UrlService) {
  }
  ngOnInit(): void {
    console.error(" log")
    this.activatedRoute.queryParams?.subscribe(async params => {
      if (params['contextInfo']) {
        this.isModeModification = true;
        this.titre="Modification Site"
         this.siteService.getSiteById(params['contextInfo']).subscribe(()=>{
         this.siteCourant=this.siteService.getSiteCourant()
          this.myform.patchValue(this.siteCourant)
          this.majBtnActive()
        })
      } else {
        this.isModeModification = false;
        this.titre="Creation Site";
        this.majBtnActive()
      }
      this.initListbtns();
    });
  }
  reset(formToReset:any){
    this.myform.controls[formToReset]?.setValue('');
  }

  // Création des boutons: Annuler, Créer, Modifier
  private initListbtns() {
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.annuler'), Actions.ANNULER, true, false, true, true, 'keyboard_arrow_left'));
      this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.creer'), Actions.CREER, !this.isModeModification, true, true, true, 'save'));
      this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.modifier'), Actions.MODIFIER, this.isModeModification, true, true, true, 'create'));
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

  /** Action sur les boutons ENREGISTRER ou ANNULER */
  siteAction(event: Actions){
    //Le click sur le bouton ENREGISTRER
    if (event === Actions.CREER || event === Actions.MODIFIER) {
      const b= this.myform.value;
      this.siteService.enregistrerSite(this.myform.value).subscribe()
    }

    //Le click sur le bouton Annuler
    if (event === Actions.ANNULER) {
      this.modalService.ouvrirModaleAnnulation(this.urlService.getPreviousUrl(), this.isModeModification ? 'modification de site' : 'création  de site'); //Ouverture de la modale d'annulation
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




  //@ViewChildren(MatRadioButton) radioButtons!: QueryList<MatRadioButton>;



  /*setupRadioChangeHandler() {
    this.radioButtons.forEach((radioButton) => {
      radioButton.change.subscribe((event) => {
        this.handleRadioChange(event);
      });
    });
  }*/

 /* handleRadioChange(event: MatRadioChange) {
    const selectedValue = event.value;
    const parentElement = event.source._elementRef.nativeElement.closest('.row');
    const highlightElements = document.querySelectorAll('.highlight');
    highlightElements.forEach((element) => {
      element.classList.remove('highlight');
    });
    parentElement.classList.add('highlight');
  }
  */

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
























