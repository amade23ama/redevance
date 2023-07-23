import {Component, OnInit} from "@angular/core";
import {ActionBtn} from "../../../core/interfaces/actionBtn";
import {Actions} from "../../../core/enum/actions";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AppConfigService} from "../../../core/services/app-config.service";
import {ParamService} from "../../../core/services/param.service";
import {SiteService} from "../../../core/services/site.service";
import {ActivatedRoute} from "@angular/router";
import {FileValidators} from "ngx-file-drag-drop";
import {DemandeDepot} from "../../../core/interfaces/demande.depot";

@Component({
  selector: 'app-depot-creation',
  templateUrl: './depot-creation.component.html',
  styleUrls: ['./depot-creation.component.scss']
})
export class DepotCreationComponent implements  OnInit{

  titre="créaction d'un dépot";
  typeFile=".txt,.csv"
  btns: ActionBtn[] = [];
  id: FormControl = new FormControl()
  nom: FormControl = new FormControl('',[Validators.required])
  file: FormControl = new FormControl('', [FileValidators.required, FileValidators.maxFileCount(3)])
  myform: FormGroup = this.builder.group({
    nom: this.nom,
    file:this.file
  })
  constructor(private builder: FormBuilder,
              public appConfig:AppConfigService,private paramService: ParamService,
              public siteService:SiteService, private readonly activatedRoute: ActivatedRoute) {
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
    formData.append('file', file, file.name);
    console.log("File changed!");
  }
  depotAction(event: Actions){
    if (event === Actions.ENREGISTRER) {
      const demande:DemandeDepot=new DemandeDepot()
            demande.nom=this.myform.value.nom;
            demande.file=this.myform.value.file[0];
            demande.data=null
    }
  }
}
