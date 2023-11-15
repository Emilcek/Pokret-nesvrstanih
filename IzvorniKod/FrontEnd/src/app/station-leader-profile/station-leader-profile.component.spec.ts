import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StationLeaderProfileComponent } from './station-leader-profile.component';

describe('StationLeaderProfileComponent', () => {
  let component: StationLeaderProfileComponent;
  let fixture: ComponentFixture<StationLeaderProfileComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StationLeaderProfileComponent]
    });
    fixture = TestBed.createComponent(StationLeaderProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
