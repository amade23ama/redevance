import { DatePipe } from "@angular/common";
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl } from "@angular/forms";
import { MatDialog } from "@angular/material/dialog";
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from "@angular/router";
import { debounceTime, distinctUntilChanged, switchMap } from "rxjs";
import { Produit } from 'src/app/core/interfaces/produit';
import { ModalService } from "src/app/core/services/modal.service";
import { ProduitService } from 'src/app/core/services/produit.service';
import { BuilderDtoJsonAbstract } from "../../../core/interfaces/BuilderDtoJsonAbstract";
import { AutocompleteRecherche } from "../../../core/interfaces/autocomplete.recherche";
import { CritereRecherche } from "../../../core/interfaces/critere.recherche";
import { AppConfigService } from "../../../core/services/app-config.service";
import { AutocompleteRechercheService } from "../../../core/services/autocomplete.recherche.service";
import { SuppressionComponent } from "../../shared-Module/dialog/suppression/suppression.component";

@Component({
  selector: 'app-recherche-produit',
  templateUrl: './recherche-produit.component.html',
  styleUrls: ['./recherche-produit.component.scss']
})
export class RechercheProduitComponent implements OnInit {
  search:FormControl =new FormControl('');
  /** la liste des Produits */
  listProduit: MatTableDataSource<Produit>;

  // La pagination
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  // nombre de ligne par page
  //pageSizeOptions: number[] = [10, 20, 30];
  pageSize = 10; // nb ligne par page par défaut
  itemSize:number=0;
  totalItems = 10;
  page = 0;
  size = 10;
  itemsPerPage = 10;
  newPage=0
  croll:boolean=false;
  private lastScrollIndex = 0;
  nb$=this.produitService.nbProduit$;
  // les noms des colones
  displayedColumns: string[] = ['id','nomSRC', 'densiteGRM', 'densiteKGM','dateCreation','actions'];
  produits$=this.produitService.produits$;
  rechercheSuggestions$=this.autocompleteRechercheService.autoCompleteRecherchesProduit$
  critereRecherches$=this.autocompleteRechercheService.critereRecherchesProduit$
  /** constructor */
  constructor(public appConfig: AppConfigService,private produitService: ProduitService,private router:Router, public dialog: MatDialog,
              private autocompleteRechercheService:AutocompleteRechercheService, private modalService: ModalService){
  }

  ngOnInit(): void {
    this.produitService.setProduits([]);
    this.rechargementProduit();
    this.produitService.produits$.subscribe((data) => {
      //alimentation du tableau
    this.listProduit = new MatTableDataSource<Produit>(data);
    //this.listProduit.paginator=this.paginator;
    this.listProduit.sort=this.sort;
    this.itemSize=data.length
    this.totalItems = 100;
    });
    this.search.valueChanges?.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((capture) => {
        this.page=0
        this.newPage=0;
        this.croll=false;
        return this.autocompleteRechercheService.autocompleteProduit(capture);
      })
    ).subscribe();

  }

  redirect(produit: Produit) {
    console.log("produit: ", produit);
  }
  chargerProduit(produit: Produit){
    this.router.navigate(['admin/produit'], {queryParams: {'contextInfo':produit.id }});
  }
  creeNouveauProduit(){
    this.router.navigate(['admin/produit']);
  }
  initial(produit: Produit){
    return produit.nomNORM.charAt(0).concat(produit.nomSRC.charAt(0)).toUpperCase();
  }
  formatDate(dateCreation: Date) {
    return (new DatePipe(BuilderDtoJsonAbstract.JSON_DATE_PIPE_LOCALE))
      .transform(dateCreation, BuilderDtoJsonAbstract.DATE_FORMAT_SIMPLEJSON);
  }
  ajouterFiltre(autocompleteRecherche:AutocompleteRecherche){
    this.autocompleteRechercheService.addAutocompleteRechercheProduit(autocompleteRecherche)
  }
  annulerFiltre(autocompleteRecherche:AutocompleteRecherche){
    this.autocompleteRechercheService.removeAutocompleteRechercheProduit(autocompleteRecherche)
  }
  rechargementProduit(){
    this.critereRecherches$.subscribe((res)=>{
      if(res) {
        const critereRecherche   = {
          autocompleteRecherches:res,
          page :this.newPage,
          size :this.size,
          dateDebut :new Date(),
          dateFin :new Date(),
        } as CritereRecherche
        this.produitService.chargementProduitParCritere(critereRecherche, this.croll).subscribe()
      }
    })
  }

  /**
   * supprimerProduit
   * @param produit
   */
  supprimerProduit(produit: Produit){

    const dialogRef = this.dialog.open(SuppressionComponent, {
      width: '600px',
      position: {top:'200px'},
      data: {name: "le produit ".concat(produit.nomSRC), id: produit.id},
    });

    dialogRef.afterClosed().subscribe(result => {
        if (result) {
          this.produitService.supprimerProduits(produit.id).subscribe((idDelete) => {
            if (idDelete) {
              this.rechargementProduit();
              this.modalService.ouvrirModalConfirmation("Produit supprimé")
            }
          });
        }
    });
  }

  onScrollEnd(index: number) {
    const isScrollingDown = index > this.lastScrollIndex;
    this.lastScrollIndex = index;
    if (isScrollingDown) {
      this.page++
      const totalLoadedItems = this.page * this.itemsPerPage;
      const newIndex = Math.floor(totalLoadedItems / this.itemsPerPage)
      this.newPage=newIndex;
      this.croll=true
      this.rechargementProduit();
    }
  }

  getItemSize() {
    return 50;
  }
}
