import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RechercheDepotComponent } from './recherche-depot.component';
import {HttpClientModule} from "@angular/common/http";

describe('RechercheDepotComponent', () => {
  let component: RechercheDepotComponent;
  let fixture: ComponentFixture<RechercheDepotComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RechercheDepotComponent ],
      imports: [HttpClientModule],
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
