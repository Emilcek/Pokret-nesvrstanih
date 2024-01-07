import {AfterViewInit, Component, OnInit} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import * as L from 'leaflet';
import {icon, Icon, LatLng, Marker} from 'leaflet';
import 'leaflet-routing-machine';
import * as polyline from 'polyline';


@Component({
  selector: 'app-station-choosing',
  templateUrl: './station-choosing.component.html',
  styleUrls: ['./station-choosing.component.css']
})
export class StationChoosingComponent implements OnInit, AfterViewInit{
  isStationChosen: boolean = false;
  chosenStation: any;
  chosenStationName: any;
  chosenStationSurface: any;
  private map: any;
  header = new HttpHeaders({
  'Authorization': 'Bearer ' + localStorage.getItem('token'),
   //'Content-Type': 'application/json',
  });
  headersObj = {
  headers: this.header
};

  private defaultIcon: Icon = icon({
    iconUrl: "https://unpkg.com/leaflet@1.5.1/dist/images/marker-icon.png"
  });

  constructor(private http: HttpClient) {
    Marker.prototype.options.icon = this.defaultIcon;
  }
  ngOnInit(): void {

    this.http.get<any>(environment.BASE_API_URL + "/stationLead/stations", this.headersObj).subscribe({
      next: (data: any) => {
        let stationsArray = new Array(7);
        for (let station of data) {
          console.log(station);
          let [latitude, longitude] = station.stationLocation.split(',').map(Number);
          let latLng = { lat: latitude, lng: longitude };

          let circle = L.circle(latLng, {
            radius: station.radius * 100,
            color: 'red',
            fillColor: '#f03'
          }).addTo(this.map);

          circle.on('mouseover', function (e) {
            circle.bindTooltip(station.stationName).openTooltip();
          });

          circle.on('mouseout', function (e) {
            circle.closeTooltip();
          });

          circle.on('click', (e) => {
            console.log("click");
            this.chosenStationName = station.stationName;
            this.chosenStationSurface = Math.round(Math.pow(station.radius, 2) * Math.PI)
            this.isStationChosen = true;
            this.chosenStation = station;
          });
        }
      }})
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

  saveStation() {
    this.http.get<any>(environment.BASE_API_URL + "/stationLead", this.headersObj).subscribe({
      next: (data: any) => {
        console.log(data)
        let stationName = this.chosenStation.stationName;
        console.log(this.chosenStation) // saljem istu postaju samo stationLead više nije null

        //this.http.get<any>(environment.BASE_API_URL + "/stationLead/myStation", this.headersObj).subscribe({
        //  next: (data: any) => {
        //    console.log("dataaaaaaaaaaa" + data)
        //  }
        //}) dohvacanje moje stanice

        // this.http.put<any>(environment.BASE_API_URL + "/stationLead/myStation/removeExplorer/" +explorerName, stationName, this.headersObj).subscribe({
        //  next: (data: any) => {
        //    console.log("dataaaaaaaaaaa" + data)
        //  }
        // })// remove i add explorer su isti samo se razlikuju u putanji

        // this.http.get<any>(environment.BASE_API_URL + "/stationLead/availableExplorers", headersObj).subscribe({
        //   next: data => {
        //     console.log("bbbbb:",data)
        //   }
        // })

        this.http.put(environment.BASE_API_URL + "/stationLead/myStation/" + stationName,{}, this.headersObj).subscribe({
          next: (putData: any) => {
            console.log("PUT request successful", putData);
          },
          error: (error) => {
            console.error("Error in PUT request", error);
          }
        });
      }})
  }
}
