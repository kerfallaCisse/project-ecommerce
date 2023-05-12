import { TestBed } from '@angular/core/testing';

import { CustomizationService } from './customization.service';

describe('PersonalisationService', () => {
  let service: CustomizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CustomizationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
