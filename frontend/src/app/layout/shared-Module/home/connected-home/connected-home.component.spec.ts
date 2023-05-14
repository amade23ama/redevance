import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConnectedHomeComponent } from './connected-home.component';

describe('ConnectedHomeComponent', () => {
  let component: ConnectedHomeComponent;
  let fixture: ComponentFixture<ConnectedHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConnectedHomeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConnectedHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});