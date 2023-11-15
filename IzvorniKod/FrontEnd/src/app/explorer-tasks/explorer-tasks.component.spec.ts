import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExplorerTasksComponent } from './explorer-tasks.component';

describe('ExplorerTasksComponent', () => {
  let component: ExplorerTasksComponent;
  let fixture: ComponentFixture<ExplorerTasksComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExplorerTasksComponent]
    });
    fixture = TestBed.createComponent(ExplorerTasksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
