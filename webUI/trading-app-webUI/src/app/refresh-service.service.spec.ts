import { TestBed } from '@angular/core/testing';

import { RefreshService } from './refresh-service.service';

describe('RefreshServiceService', () => {
  let service: RefreshService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RefreshService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
