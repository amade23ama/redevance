import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RechercheTableComponent } from './recherche-table.component';

describe('RechercheTableComponent', () => {
  let component: RechercheTableComponent;
  let fixture: ComponentFixture<RechercheTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RechercheTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RechercheTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
