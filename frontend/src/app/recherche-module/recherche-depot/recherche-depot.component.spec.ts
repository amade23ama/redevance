import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RechercheDepotComponent } from './recherche-depot.component';

describe('RechercheDepotComponent', () => {
  let component: RechercheDepotComponent;
  let fixture: ComponentFixture<RechercheDepotComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RechercheDepotComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RechercheDepotComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
