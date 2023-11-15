import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserVerifiedSuccessfulComponent } from './user-verified-successful.component';

describe('UserVerifiedSuccessfulComponent', () => {
  let component: UserVerifiedSuccessfulComponent;
  let fixture: ComponentFixture<UserVerifiedSuccessfulComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserVerifiedSuccessfulComponent]
    });
    fixture = TestBed.createComponent(UserVerifiedSuccessfulComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
