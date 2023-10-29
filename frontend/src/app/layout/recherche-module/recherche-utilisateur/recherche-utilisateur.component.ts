import { DatePipe } from "@angular/common";
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl } from "@angular/forms";
import { MatDialog } from "@angular/material/dialog";
import { MatPaginator } from "@angular/material/paginator";
import { MatSort } from "@angular/material/sort";
import { MatTableDataSource } from "@angular/material/table";
import { Router } from "@angular/router";
import { debounceTime, distinctUntilChanged, switchMap } from "rxjs";
import { ModalService } from "src/app/core/services/modal.service";
import { BuilderDtoJsonAbstract } from "../../../core/interfaces/BuilderDtoJsonAbstract";
import { AutocompleteRecherche } from "../../../core/interfaces/autocomplete.recherche";
import { CritereRecherche } from "../../../core/interfaces/critere.recherche";
import { Filtre } from "../../../core/interfaces/filtre";
import { Profil } from "../../../core/interfaces/profil";
import { Utilisateur } from "../../../core/interfaces/utilisateur";
import { AppConfigService } from "../../../core/services/app-config.service";
import { AutocompleteRechercheService } from "../../../core/services/autocomplete.recherche.service";
import { ParamService } from "../../../core/services/param.service";
import { UtilisateurService } from "../../../core/services/utilisateur.service";
import { DialogueComponent } from "../../shared-Module/dialog/dialogue/dialogue.component";

@Component({
  selector: 'app-recherche-utilisateur',
  templateUrl: './recherche-utilisateur.component.html',
  styleUrls: ['./recherche-utilisateur.component.scss']
})
export class RechercheUtilisateurComponent implements OnInit{
  search:FormControl =new FormControl('');
  label="Ajouter un Utilisateur";
  dataSource: MatTableDataSource<Utilisateur>;
  prenom="prenom"
  pageSizeOptions: number[] = [10, 20, 30];
  rechercheUtilisateurListe: Utilisateur[] = [];
  displayedColumns: string[] = ['id','prenom', 'nom', 'email','profil','dateCreation',"actions"];
  pageSize = 10;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  users$=this.utilisateurService.utilisateurs$
  profils$ = this.paramService.profils$;
  profil:Profil[]=[];
  filtres:Filtre[]=[]
  recherche: AutocompleteRecherche[] = [];
  rechercheSuggestions$=this.autocompleteRechercheService.autoCompleteRecherches$
  critereRecherches$=this.autocompleteRechercheService.critereRecherches$

  constructor(public appConfig: AppConfigService,private readonly utilisateurService: UtilisateurService,
            private router:Router,private paramService: ParamService, public dialog: MatDialog, public modalService: ModalService,
            private autocompleteRechercheService:AutocompleteRechercheService) {
}
  ngOnInit(): void {
    //this.utilisateurService.getAllUtilisateur().subscribe()
    this.paramService.chargementProfils().subscribe()
    this.utilisateurService.utilisateurs$.subscribe((res)=>{
      if (res !== null) {
        this.rechercheUtilisateurListe = res;
        this.dataSource= new MatTableDataSource<Utilisateur>(this.rechercheUtilisateurListe);
        this.dataSource.paginator=this.paginator
        this.dataSource.sort=this.sort
      }
    })

    this.search.valueChanges?.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((capture) => {
          return this.autocompleteRechercheService.autocompleteUtilisateur(capture);
      })
    ).subscribe();
    this.rechargementUtilisateur();
  }
  /**
   * Méthode de formatage de la date
   * @param dateCreation
   */
  formatDate(dateCreation: Date) {
    return (new DatePipe(BuilderDtoJsonAbstract.JSON_DATE_PIPE_LOCALE))
      .transform(dateCreation, BuilderDtoJsonAbstract.DATE_FORMAT_SIMPLEJSON);
  }

  convertToLowercase(text:string){
     this.profils$.subscribe((res)=>{
       this.profil=res;
     })

    if(this.profil){
      return this.profil.filter((res)=>res?.code==text)[0]?.libelle.toLowerCase();
    }
    return text.toLowerCase();
  }



  chargerUtilisateur(utilisteur:Utilisateur){
    this.router.navigate(['admin/utilisateur'], {queryParams: {'contextInfo':utilisteur.id }});
}
  ouvreNouveauUtilisateur(){
    this.utilisateurService.purgerUtilisateur()
    this.router.navigate(['admin/utilisateur']);
  }
  initial(utilisteur:Utilisateur){
    return utilisteur.prenom.charAt(0).concat(utilisteur.nom.charAt(0)).toUpperCase()
  }
  supprimer(filtre:Filtre){
    const index = this.filtres.indexOf(filtre);
    if (index >= 0) {
      this.filtres.splice(index, 1);
    }
  }

  ajouterFiltre(autocompleteRecherche:AutocompleteRecherche){
    this.autocompleteRechercheService.addAutocompleteRecherche(autocompleteRecherche)
  }
  annulerFiltre(autocompleteRecherche:AutocompleteRecherche){
    this.autocompleteRechercheService.removeAutocompleteRecherche(autocompleteRecherche)
  }
  rechargementUtilisateur(){
    this.critereRecherches$.subscribe((res)=>{
      if(res) {
        const critereRecherche   = {
        autocompleteRecherches:res,
        page :1,
        size :20,
        dateDebut :new Date(),
        dateFin :new Date(),
      } as CritereRecherche
        this.utilisateurService.chargementUtilisateurParCritere(critereRecherche).subscribe()
      }

    })
  }

  /**
   * Activation ou désactivation d'un utilisateur
   * @param utilisteur
   */
  desableUser( utilisteur: Utilisateur){

    if (utilisteur.active) {
      const dialogRef = this.dialog.open(DialogueComponent, {
        width: '600px',
        position: {top:'200px'},
        data: {title: this.appConfig.getLabel('modal.dialog.desactivation.title'),
        question: this.appConfig.getLabel('modal.dialog.desactivation.question', utilisteur.prenom +' - ' + utilisteur.nom)},
      });

      dialogRef.afterClosed().subscribe(result => {

        if (result) {
          let user = utilisteur;
              user.active = false;
              this.utilisateurService.enregistrer(user).subscribe((user)=>{
              this.modalService.ouvrirModalConfirmation(this.appConfig.getLabel('modal.confirmation.desactivation', utilisteur.prenom +' - ' + utilisteur.nom));
            })
        }
      });

    }else{
      const dialogRef = this.dialog.open(DialogueComponent, {
        width: '600px',
        position: {top:'200px'},
        data: {title: this.appConfig.getLabel('modal.dialog.activation.title'),
        question: this.appConfig.getLabel('modal.dialog.activation.question', utilisteur.prenom +' - ' + utilisteur.nom)},
      });

      dialogRef.afterClosed().subscribe(result => {

        if (result) {
          let user = utilisteur;
              user.active = true;
              this.utilisateurService.enregistrer(user).subscribe((user)=>{
              this.modalService.ouvrirModalConfirmation(this.appConfig.getLabel('modal.confirmation.activation', utilisteur.prenom +' - ' + utilisteur.nom));
            })
        }
      });

    }

  }
}
