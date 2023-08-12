import {AfterViewInit, Component, ElementRef, OnInit, QueryList, ViewChild, ViewChildren} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ParamService} from "../../../core/services/param.service";
import {AppConfigService} from "../../../core/services/app-config.service";
import {debounceTime, distinctUntilChanged, map, Observable, of, startWith, tap} from "rxjs";
import {animate, group, state, style, transition, trigger} from "@angular/animations";
import {openCloseTransition} from "../../../core/interfaces/open-close.transition";
import {MatRadioButton, MatRadioChange} from "@angular/material/radio";
import {ActionBtn} from "../../../core/interfaces/actionBtn";
import {Actions} from "../../../core/enum/actions";
import {Produit} from "../../../core/interfaces/produit";
import {Site} from "../../../core/interfaces/site";
import {SiteService} from "../../../core/services/site.service";
import {ActivatedRoute} from "@angular/router";



@Component({
  selector: 'app-site',
  templateUrl: './site.component.html',
  styleUrls: ['./site.component.scss'],
  animations: [openCloseTransition]
})
export class SiteComponent implements OnInit {
  titre="Creer un Nouveau  Site"
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
  constructor(private builder: FormBuilder,
              public appConfig:AppConfigService,private paramService: ParamService,
              public siteService:SiteService, private readonly activatedRoute: ActivatedRoute) {
  }
  ngOnInit(): void {
    console.error(" log")
    this.activatedRoute.queryParams?.subscribe(async params => {
      this.initListbtns();
      if (params['contextInfo']) {
        this.titre="Modification Site"
         this.siteService.getSiteById(params['contextInfo']).subscribe(()=>{
         this.siteCourant=this.siteService.getSiteCourant()
          this.myform.patchValue(this.siteCourant)
          this.majBtnActive()
        })
      } else {
        this.titre="Creation Site";
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

  utilisateurAction(event: Actions){
    if (event === Actions.ENREGISTRER) {
      const b= this.myform.value;
      this.siteService.enregistrerSite(this.myform.value).subscribe()
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

}
























