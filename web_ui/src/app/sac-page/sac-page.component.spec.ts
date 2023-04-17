import { ComponentFixture, TestBed } from '@angular/core/testing';

import {SacPageComponent} from './sac-page.component';

describe('SacPageComponent', () => {
  let component: SacPageComponent;
  let fixture: ComponentFixture<SacPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SacPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SacPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
