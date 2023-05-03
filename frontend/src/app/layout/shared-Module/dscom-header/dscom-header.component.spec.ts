import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DscomHeaderComponent } from './dscom-header.component';

describe('DscomHeaderComponent', () => {
  let component: DscomHeaderComponent;
  let fixture: ComponentFixture<DscomHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DscomHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DscomHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
