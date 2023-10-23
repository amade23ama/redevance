import { Component, OnInit } from "@angular/core";
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { Actions } from "../../../core/enum/actions";
import { ActionBtn } from "../../../core/interfaces/actionBtn";
import { Categorie } from "../../../core/interfaces/categorie";
import { AppConfigService } from "../../../core/services/app-config.service";
import { CategorieService } from "../../../core/services/categorie.service";
import { ModalService } from "../../../core/services/modal.service";
import { UrlService } from "../../../core/services/url.service";

@Component({
  selector: 'app-classe-voiture',
  templateUrl: './classe-voiture.component.html',
  styleUrls: ['./classe-voiture.component.scss']
})
export class ClasseVoitureComponent implements OnInit{
  titre="Classe Vehicule";
  btns: ActionBtn[] = [];
  id: FormControl = new FormControl();
  nom: FormControl = new FormControl('',[Validators.required]);
  volume: FormControl = new FormControl('',[Validators.required, this.isPositiveNumber.bind(this)]);
  categorieId: FormControl = new FormControl();
  type: FormControl = new FormControl('',[Validators.required]);
  myform:FormGroup = this.builder.group({
    id:this.categorieId,
    volume:this.volume,
    type:this.type
  })
  categorieCourant:Categorie;

  // indique si on est en modification
  isModeModification = false;
  constructor(public builder:FormBuilder,public appConfig:AppConfigService,
              private readonly activatedRoute: ActivatedRoute,
              public modalService: ModalService, public urlService: UrlService,
              private categorieService:CategorieService) {
  }

  ngOnInit(): void {
    this.activatedRoute.queryParams?.subscribe(async params => {
      if (params['contextInfo']) {
        //this.titre="Modification Site"
        this.isModeModification = true;
        this.categorieService.getCategorieParId(params['contextInfo']).subscribe(()=>{
          this.categorieCourant=this.categorieService.getCategorieCourant()
          this.myform.patchValue(this.categorieCourant)
          this.majBtnActive()
        })
      } else {
        this.isModeModification = false;
        //this.titre="Creation Site";
        this.majBtnActive()
      }
      this.initListbtns();
    });
  }

  reset(formToReset:string){
    this.myform.controls[formToReset]?.setValue('');
  }



  annuler(){
    console.log(" annuller")
  }
  categorieAction(event: Actions){
    //Le click sur le bouton Enregistrer
    if (event === Actions.CREER) {
      this.categorieService.enregistrerCategorie(this.myform.value).subscribe()
    }

    if (event === Actions.MODIFIER) {
      let classAmodifie = this.categorieCourant;
      classAmodifie.type = this.getType?.value;
      classAmodifie.volume = this.getVolume?.value;
      this.categorieService.enregistrerCategorie(classAmodifie).subscribe()
    }

    //Le click sur le bouton Annuler
    if (event === Actions.ANNULER) {
      this.modalService.ouvrirModaleAnnulation(this.urlService.getPreviousUrl(), this.isModeModification ? 'modification de véhicule' : 'création de véhicule'); //Ouverture de la modale d'annulation
    }
  }
  private initListbtns() {
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.annuler'), Actions.ANNULER, true, false, false, true, 'keyboard_arrow_left'));
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.creer'), Actions.CREER, !this.isModeModification, true, true, true, 'save'));
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.modifier'), Actions.MODIFIER, this.isModeModification, true, true, true, 'create'));
    return this.btns;
  }

  isEnrgBtnDisplayed(){
    this.categorieCourant = this.categorieService.getCategorieCourant();

    return !this.categorieCourant?.id ? true : false;
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

  /**
   * isPositiveNumber
   * @param control isPositiveNumber
   * @returns isNotPositive
   */
  isPositiveNumber(control: AbstractControl){
    return this.volume?.value >= 0 ? null: { isNotPositive: true }
  }

    // get data
    get getType(): AbstractControl { return this.myform?.get("type");}
    get getVolume(): AbstractControl { return this.myform?.get("volume");}
}
