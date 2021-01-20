import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeathComponent } from './homeath.component';

describe('HomeathComponent', () => {
  let component: HomeathComponent;
  let fixture: ComponentFixture<HomeathComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomeathComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeathComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
