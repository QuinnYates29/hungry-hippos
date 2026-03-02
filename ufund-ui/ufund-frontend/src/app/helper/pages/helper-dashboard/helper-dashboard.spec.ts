import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HelperDashboard } from './helper-dashboard';

describe('HelperDashboard', () => {
  let component: HelperDashboard;
  let fixture: ComponentFixture<HelperDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HelperDashboard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HelperDashboard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
