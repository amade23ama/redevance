import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RechercheVehiculeComponent } from './recherche-vehicule.component';

describe('RechercheVehiculeComponent', () => {
  let component: RechercheVehiculeComponent;
  let fixture: ComponentFixture<RechercheVehiculeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RechercheVehiculeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RechercheVehiculeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
