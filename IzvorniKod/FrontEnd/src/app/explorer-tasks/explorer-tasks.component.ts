import {Component, OnInit} from '@angular/core';
import {HeaderService} from "../header/header.service";

@Component({
  selector: 'app-explorer-tasks',
  templateUrl: './explorer-tasks.component.html',
  styleUrls: ['./explorer-tasks.component.css']
})
export class ExplorerTasksComponent implements OnInit{
  constructor(private headerService: HeaderService) {
  }

  ngOnInit() {

  }
}
