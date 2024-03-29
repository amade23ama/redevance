import { Component, Inject, OnInit } from "@angular/core";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { Router } from "@angular/router";
import { Actions } from "../../../core/enum/actions";
import { ActionBtn } from "../../../core/interfaces/actionBtn";
import { AppConfigService } from "../../../core/services/app-config.service";
import { DepotService } from "../../../core/services/depot.service";

@Component({
  selector: 'app-depot-validation-column-popup',
  templateUrl: './depot-validation-column-popup.component.html',
  styleUrls: ['./depot-validation-column-popup.component.scss']
})
export class DepotValidationColumnPopupComponent implements OnInit{
  btns: ActionBtn[] = [];
  mappingForm: FormGroup;
  fichierColonnes: string[] = [];
  dbColonnes: string[] = [];
  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              public appConfig: AppConfigService,
              private router: Router,
              public dialogRef: MatDialogRef<DepotValidationColumnPopupComponent>,
              public depotService: DepotService,
              private builder: FormBuilder,) {
  }
  ngOnInit(): void {
    this.initListbtns()
    this.initierdbColonnes()
    this.initierfichierColonnes()
    this.createForm()
    this.majBtnActive()
    this.setCollumnCorrespondantFichierdb()
  }
  private initListbtns() {
    this.btns.push(new ActionBtn(this.appConfig.getLabel('dcsom.actions.annuler'),
      Actions.ANNULER, true, false, false, true, 'keyboard_arrow_left'));
    this.btns.push(new ActionBtn(this.appConfig.getLabel('depot.actions.valider'),
      Actions.ENREGISTRER, this.isEnrgBtnDisplayed(), true, true, true, 'done_outline'));
    return this.btns;
  }
  isEnrgBtnDisplayed(){
    return true
  }
  validerAction(event: Actions){
    console.log(" tes",this.mappingForm)
    if (event === Actions.ENREGISTRER) {
      this.data.file.append('mapEntete',JSON.stringify(Object.fromEntries(this.formGroupToMap())));
      this.depotService.deposerFichier(this.data.file).subscribe(()=>{})
      this.dialogRef.close();
      //On affiche le tableau des importations lorsque le chargement est envoyé en base de données.
      this.router.navigate(['recherche/depots']);
    }
  }
  initierdbColonnes(){
    //this.dbColonnes=  ['dbPrenom','dbnom','dbchauffeur'];
    //todo a remplacer avec les colonnes en base
    this.dbColonnes=this.data.fileInfo.colonneTable;
  }
  initierfichierColonnes(){
   this.fichierColonnes=this.data.fileInfo.enteteFile
  }
  createForm() {
    const formGroupControls = {};
    this.fichierColonnes.forEach((column) => {
      formGroupControls[column] = new FormControl('', Validators.required);
    });
    this.mappingForm = this.builder.group(formGroupControls);
  }
  formGroupToMap(): Map<string, any> {
    const formValues = this.mappingForm.value;
    return new Map(Object.entries(formValues));
  }


  majBtnActive(){
    this.mappingForm?.valueChanges.subscribe((res)=>{
      if(this.mappingForm.invalid){
        this.btns.forEach(b=>{
          b.disabled=true
        });
      }else{
        this.btns.forEach(b=>{
          b.disabled=false
        });
      }
    })
    if(!this.mappingForm.invalid){
      this.btns.forEach(b=>{
        b.disabled=false
      });
    }
  }
  setCollumnCorrespondantFichierdb(){
    this.fichierColonnes.forEach(entete => {
      const cleanedEntete = entete.toLowerCase().replace(/[\s.]+/g, '')
      const colonneCorrespondante = this.dbColonnes.find(colonne => colonne.toLowerCase().includes(cleanedEntete.toLowerCase()));
      if (colonneCorrespondante && this.mappingForm.get(entete) && cleanedEntete!=="poids") {
        this.mappingForm.controls[entete].setValue(colonneCorrespondante);
      }
      else if(("Prov").toLowerCase().includes(cleanedEntete.toLowerCase())){
        const res = this.dbColonnes.find(colonne => colonne.toLowerCase().includes(("Origine").toLowerCase()));
        if (res!=null) {
          this.mappingForm.controls[entete].setValue(res);
        }
      }
      else if(("Poids Maximum").toLowerCase().replace(/[\s.]+/g, '').includes(cleanedEntete) && cleanedEntete!=="poids"){
        const res = this.dbColonnes.find(colonne => colonne.toLowerCase().includes(("Poids Maximum").toLowerCase()));
        if (res!=null) {
          this.mappingForm.controls[entete].patchValue(res);
        }
      }
      else if("poids"===cleanedEntete){
        const res = this.dbColonnes.find(colonne => colonne.toLowerCase().includes(("Poids mesuré").toLowerCase()));
        if (res!=null) {
          this.mappingForm.controls[entete].setValue(res);
        }
      }

      else if(cleanedEntete.toLowerCase().includes(("produits").toLowerCase())){
        const res = this.dbColonnes.find(colonne => colonne.toLowerCase().includes(("Produit").toLowerCase()));
        if (res!=null) {
          this.mappingForm.controls[entete].setValue(res);
        }
      }
      else if(cleanedEntete.includes("Proprietaire".toLowerCase())){
        const res = this.dbColonnes.find(colonne => colonne.toLowerCase().includes(("transporteur").toLowerCase()));
        if (res!=null) {
          this.mappingForm.controls[entete].patchValue(res);
        }
      }
     else {
        this.mappingForm.get(entete).setValue("Ignorer");
      }

    });
  }
}
