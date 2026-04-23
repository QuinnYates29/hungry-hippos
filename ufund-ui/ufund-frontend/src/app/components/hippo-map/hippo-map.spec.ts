import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HippoMap } from './hippo-map';

describe('HippoMap', () => {
  let component: HippoMap;
  let fixture: ComponentFixture<HippoMap>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HippoMap]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HippoMap);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
