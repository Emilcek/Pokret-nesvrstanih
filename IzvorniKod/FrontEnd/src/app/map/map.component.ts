import { Component, AfterViewInit } from '@angular/core';
import * as L from 'leaflet';
import {HttpClient} from "@angular/common/http";
import 'leaflet-routing-machine';
import {icon, Icon, Marker} from "leaflet";
import * as polyline from 'polyline';

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

    /*this.http.get<any>('https://router.project-osrm.org/route/v1/foot/49.52334,3.186035;51.061132,7.514648?overview=false&alternatives=true').subscribe({
      next: data => {
        let resData: any = data;
        console.log(data)
        L.Routing.control({waypoints: [L.latLng(resData.waypoints[0].location[0], resData.waypoints[0].location[1]), L.latLng(resData.waypoints[1].location[0], resData.waypoints[1].location[1])]}).addTo(this.map)
      }
      }
    )*/

    this.http.get<any>('http://router.project-osrm.org/route/v1/walking/13.388860,52.517037;13.397634,52.529407;13.428555,52.523219;13.388860,52.517037?overview=full').subscribe({
        next: data => {
          const encodedPolyline = data.routes[0].geometry;
          console.log(encodedPolyline)
          const routeCoordinates = polyline.decode(encodedPolyline).map((coord) => L.latLng(coord[0], coord[1]));
          console.log(routeCoordinates)

          const routePolyline = L.polyline(routeCoordinates, { color: 'blue' });
          routePolyline.addTo(this.map);
          //L.Routing.line(data.routes[0].geometry).addTo(this.map);
          const coords = polyline.decode(encodedPolyline);
          const circle = L.circle([45.8, 16.1 ], {
            radius: 10000,
            color: 'red',
            fillColor: '#f03'
          }).addTo(this.map);

          circle.on('mouseover', function (e) {
            circle.bindTooltip('Zagreb').openTooltip();
          });

          circle.on('mouseout', function (e) {
            circle.closeTooltip();
          });

          circle.on('click', function (e) {
            console.log("click");
          });
        }
      }
    )


    tiles.addTo(this.map);
    /*L.Routing.control({
      waypoints: [L.latLng(50.2055, 11.4148), L.latLng(51.783, 6.2512)],
      routeWhileDragging: true,
    }).addTo(this.map);*/
  }

  ngAfterViewInit(): void {
    this.initMap();
  }

  errorF(args?: any) {

  }
}