import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HippoDashboard } from './hippo-dashboard';

describe('HippoDashboard', () => {
  let component: HippoDashboard;
  let fixture: ComponentFixture<HippoDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HippoDashboard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HippoDashboard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
