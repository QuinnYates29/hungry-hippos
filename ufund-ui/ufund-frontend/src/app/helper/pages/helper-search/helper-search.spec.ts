import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HelperSearch } from './helper-search';

describe('HelperSearch', () => {
  let component: HelperSearch;
  let fixture: ComponentFixture<HelperSearch>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HelperSearch]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HelperSearch);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
