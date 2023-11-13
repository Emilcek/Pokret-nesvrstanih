import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserDataEditingComponent } from './user-data-editing.component';

describe('UserDataEditingComponent', () => {
  let component: UserDataEditingComponent;
  let fixture: ComponentFixture<UserDataEditingComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserDataEditingComponent]
    });
    fixture = TestBed.createComponent(UserDataEditingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
