import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {HeaderAuthComponent} from './header-auth.component';

describe('HeaderauthComponent', () => {
  let component: HeaderAuthComponent;
  let fixture: ComponentFixture<HeaderAuthComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [HeaderAuthComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderAuthComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
