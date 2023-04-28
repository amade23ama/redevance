import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DscomFooterComponent } from './dscom-footer.component';

describe('DscomFooterComponent', () => {
  let component: DscomFooterComponent;
  let fixture: ComponentFixture<DscomFooterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DscomFooterComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DscomFooterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
