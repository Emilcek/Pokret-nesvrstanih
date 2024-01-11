import {Component, Inject,AfterViewInit,OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import * as L from 'leaflet';
import 'leaflet-routing-machine';
@Component({
  selector: 'app-action-details',
  templateUrl: './action-details.component.html',
  styleUrls: ['./action-details.component.css']
})
export class ActionDetailsComponent implements OnInit,AfterViewInit{
  constructor(@Inject(MAT_DIALOG_DATA) public data: any) { }
  tasks: any = [];
  taskStatus: any = {'Ongoing': 'U tijeku', 'Done': 'Izvršen'};
  private map: any;
  private center: L.LatLngExpression = L.latLng(45.1, 15.2);
  chosenStationName: any;
  startLocation:any;
  endLocation:any;
  taskAdded: any=false;
  markersGroup:any;
  ngOnInit(): void {

  }
  private initMap(): void {
    this.map = L.map('map', {
      center: this.center,
      zoom: 7
    });

    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright"></a>'
    });


    var customIcon = L.icon({
      iconUrl: "https://unpkg.com/leaflet@1.5.1/dist/images/marker-icon.png", // Specify the path to your custom icon image
      iconSize: [22, 32], // Set the size of the icon
      iconAnchor: [16, 32], // Set the anchor point of the icon (relative to its size)
      popupAnchor: [0, -32] // Set the anchor point for popups (relative to its size)
    });

    var baseMaps = {
      "OpenStreetMap": tiles
    }

    tiles.addTo(this.map);

    // a layer group, used here like a container for markers
    this.markersGroup = L.layerGroup();
    this.map.addLayer(this.markersGroup);
    let e = L.latLng(45.1, 15.2);
    var markersCount = this.markersGroup.getLayers().length;
    var marker = L.marker(e,{icon:customIcon}).addTo(this.markersGroup);

  }
  ngAfterViewInit(): void {
      this.initMap();
      console.log(this.data,"nigga")
  }

}
