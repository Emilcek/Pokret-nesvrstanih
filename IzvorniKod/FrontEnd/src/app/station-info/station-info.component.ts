import {Component, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-station-info',
  templateUrl: './station-info.component.html',
  styleUrls: ['./station-info.component.css']
})
export class StationInfoComponent implements OnInit {
  stationName: any;
  stationSurface: any;
  tragaci = new FormControl();
  tragaci2: any;
  ngOnInit(): void {
    this.stationName = "Kopački rit"
    this.stationSurface = 93000;
    this.tragaci2 = ["pero", "pero2"];
  }


}
