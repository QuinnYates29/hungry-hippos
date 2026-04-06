import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HipposDashboard } from './hippos-dashboard';

describe('HipposDashboard', () => {
  let component: HipposDashboard;
  let fixture: ComponentFixture<HipposDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HipposDashboard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HipposDashboard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
