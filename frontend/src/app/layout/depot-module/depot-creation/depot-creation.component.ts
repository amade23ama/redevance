import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { MatDialog } from "@angular/material/dialog";
import { ActivatedRoute } from "@angular/router";
import { FileValidators } from "ngx-file-drag-drop";
import { ModalService } from "src/app/core/services/modal.service";
import { UrlService } from "src/app/core/services/url.service";
import { Actions } from "../../../core/enum/actions";
import { ActionBtn } from "../../../core/interfaces/actionBtn";
import { AppConfigService } from "../../../core/services/app-config.service";
import { DepotService } from "../../../core/services/depot.service";
import { ParamService } from "../../../core/services/param.service";
import { SiteService } from "../../../core/services/site.service";

@Component({
  selector: 'app-depot-creation',
  templateUrl: './depot-creation.component.html',
  styleUrls: ['./depot-creation.component.scss']
})
export class DepotCreationComponent implements  OnInit{
  numero$=this.depotService.numeroDepot$
  titre="Créer un import";
  typeFile=".txt,.csv"
  btns: ActionBtn[] = [];
  id: FormControl = new FormControl()
  nom: FormControl = new FormControl('test',[Validators.required])
  file: FormControl = new FormControl('', [FileValidators.required, FileValidators.maxFileCount(3)])
  myform: FormGroup = this.builder.group({
    nom: this.nom,
    file:this.file
  })
  constructor(private builder: FormBuilder, public dialog: MatDialog,
              public appConfig:AppConfigService,private paramService: ParamService,
              public siteService:SiteService, private readonly activatedRoute: ActivatedRoute,
              public modalService: ModalService, public urlService: UrlService,
              public depotService:DepotService) {
  }

  ngOnInit(): void {
   this. initListbtns()
    this.majBtnActive()
    this.file.valueChanges.subscribe((files: File[]) =>
      console.log(this.file.value, this.file.valid)
    );
  }

  private initListbtns() {
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.annuler'),
      Actions.ANNULER, true, false, false, true, 'keyboard_arrow_left'));
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


  majBtnActive(){
    this.myform?.valueChanges.subscribe((res)=>{
      if(this.myform.invalid){
        this.btns.forEach(b=>{
          if(b.id!==Actions.ANNULER){
            b.disabled=true
          }

        });
      }else{
        this.btns.forEach(b=>{
          if(b.id!==Actions.ANNULER){
            b.disabled=false
          }
        });
      }
    })
    if(!this.myform.invalid){
      this.btns.forEach(b=>{
        b.disabled=false
      });
    }
  }
  reset(formToReset:any){
    this.myform.controls[formToReset]?.setValue('');
  }
  onValueChange(files: File[]) {
    const file=files[0]
    const formData = new FormData();
    formData.append('file', file);
    console.log("File changed!");
  }

  /** Action sur les boutons ENREGISTRER ou ANNULER */
  depotAction(event: Actions) {

    //Le click sur le bouton ENREGISTRER
    if (event === Actions.ENREGISTRER) {
      const file: File = this.myform.value.file[0];
      const formData: FormData = new FormData();
      formData.append('nom', JSON.stringify(this.myform.value.nom));
      formData.append('file', file);
      this.depotService.creerDepot(formData).subscribe((res)=>{
        this.depotService.ouvreValidationColumnPopUpDepot("valider la correspondant");
      })
    }

    //Le click sur le bouton Annuler
    if (event === Actions.ANNULER) {
      this.modalService.ouvrirModaleAnnulation(this.urlService.getPreviousUrl(), 'création de dépot'); //Ouverture de la modale d'annulation
    }

    this.file.setValue('');
  }
}
