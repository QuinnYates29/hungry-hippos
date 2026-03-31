import { TestBed } from '@angular/core/testing';

import { Hippo } from './hippo';

describe('Hippo', () => {
  let service: Hippo;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Hippo);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
