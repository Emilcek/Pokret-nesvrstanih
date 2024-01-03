import {Component, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";
import {icon, Icon, Marker} from "leaflet";
import {HttpClient} from "@angular/common/http";
import * as L from 'leaflet';
import 'leaflet-routing-machine';

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
  isTragaciEmpty: any;
  private map: any;

  private defaultIcon: Icon = icon({
    iconUrl: "https://unpkg.com/leaflet@1.5.1/dist/images/marker-icon.png"
  });

  constructor(private http: HttpClient) {
    Marker.prototype.options.icon = this.defaultIcon;
  }
  ngOnInit(): void {
    this.stationName = "Kopački rit"
    this.stationSurface = 93000;
    this.tragaci2 = ["pero", "pero2"];
    this.isTragaciEmpty = false;
  }

  private initMap(): void {
    this.map = L.map('map', {
      center: [ 45.1, 15.2 ],
      zoom: 7
    });


    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });
    var customIcon = L.icon({
      iconUrl: "https://unpkg.com/leaflet@1.5.1/dist/images/marker-icon.png", // Specify the path to your custom icon image
      iconSize: [22, 32], // Set the size of the icon
      iconAnchor: [16, 32], // Set the anchor point of the icon (relative to its size)
      popupAnchor: [0, -32] // Set the anchor point for popups (relative to its size)
    });


    tiles.addTo(this.map);
  }

  ngAfterViewInit(): void {
    this.initMap();
  }
}
