import {Component, OnInit} from '@angular/core';
import {HeaderService} from "../header/header.service";

@Component({
  selector: 'app-explorer-tasks',
  templateUrl: './explorer-tasks.component.html',
  styleUrls: ['./explorer-tasks.component.css']
})
export class ExplorerTasksComponent implements OnInit{
  tasks: any;

  constructor(private headerService: HeaderService) {
  }

  ngOnInit() {
    this.tasks = [
      "task 1", "task 2", "task 3"
    ]
  }
}
