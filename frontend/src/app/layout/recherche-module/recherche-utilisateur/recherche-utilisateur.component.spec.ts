import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RechercheUtilisateurComponent } from './recherche-utilisateur.component';

describe('RechercheUtilisateurComponent', () => {
  let component: RechercheUtilisateurComponent;
  let fixture: ComponentFixture<RechercheUtilisateurComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RechercheUtilisateurComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RechercheUtilisateurComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
