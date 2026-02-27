import { TestBed } from '@angular/core/testing';

import { Needs } from './needs';

describe('Needs', () => {
  let service: Needs;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Needs);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
