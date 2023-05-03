import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DscomInfoComponent } from './dscom-info.component';

describe('DscomInfoComponent', () => {
  let component: DscomInfoComponent;
  let fixture: ComponentFixture<DscomInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DscomInfoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DscomInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
