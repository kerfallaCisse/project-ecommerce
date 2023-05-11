import { TestBed } from '@angular/core/testing';

import { PersonalisationService } from './personalisation.service';

describe('PersonalisationService', () => {
  let service: PersonalisationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PersonalisationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
