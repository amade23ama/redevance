import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisconnectedHomeComponent } from './disconnected-home.component';

describe('DisconnectedHomeComponent', () => {
  let component: DisconnectedHomeComponent;
  let fixture: ComponentFixture<DisconnectedHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DisconnectedHomeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DisconnectedHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
