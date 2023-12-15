import { Component, AfterViewInit } from '@angular/core';
import * as L from 'leaflet';
import {HttpClient} from "@angular/common/http";
import 'leaflet-routing-machine';
import {icon, Icon, Marker} from "leaflet";

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements AfterViewInit {
  private map: any;

  private defaultIcon: Icon = icon({
    iconUrl: "https://unpkg.com/leaflet@1.5.1/dist/images/marker-icon.png"
  });

  constructor(private http: HttpClient) {
    Marker.prototype.options.icon = this.defaultIcon;

  }

  private initMap(): void {
    this.map = L.map('map', {
      center: [ 51.505, -0.09 ],
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

    this.http.get<any>('https://router.project-osrm.org/route/v1/bike/49.52334,3.186035;51.061132,7.514648?overview=false&alternatives=true').subscribe({
      next: data => {
        let resData: any = data;
        console.log(data)
        L.Routing.control({waypoints: [L.latLng(resData.waypoints[0].location[0], resData.waypoints[0].location[1]), L.latLng(resData.waypoints[1].location[0], resData.waypoints[1].location[1])]}).addTo(this.map)
      }
      }
    )
    tiles.addTo(this.map);
    L.Routing.control({
      waypoints: [L.latLng(57.74, 11.94), L.latLng(57.6792, 11.949)],
      routeWhileDragging: true
    }).addTo(this.map);
  }

  ngAfterViewInit(): void {
    this.initMap();
  }
}
