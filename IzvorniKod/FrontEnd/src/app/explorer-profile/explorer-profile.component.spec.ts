import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExplorerProfileComponent } from './explorer-profile.component';

describe('ExplorerProfileComponent', () => {
  let component: ExplorerProfileComponent;
  let fixture: ComponentFixture<ExplorerProfileComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExplorerProfileComponent]
    });
    fixture = TestBed.createComponent(ExplorerProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
