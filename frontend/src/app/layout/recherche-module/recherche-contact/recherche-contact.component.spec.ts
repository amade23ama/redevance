import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RechercheContactComponent } from './recherche-contact.component';

describe('RechercheContactComponent', () => {
  let component: RechercheContactComponent;
  let fixture: ComponentFixture<RechercheContactComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RechercheContactComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RechercheContactComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
