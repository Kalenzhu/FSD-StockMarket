import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IPOTableComponent } from './ipo-table.component';

describe('CompanyibComponent', () => {
  let component: IPOTableComponent;
  let fixture: ComponentFixture<IPOTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IPOTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IPOTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
