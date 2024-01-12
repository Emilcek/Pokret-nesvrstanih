import {Component, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";
import {icon, Icon, LatLng, Marker} from "leaflet";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import * as L from 'leaflet';
import 'leaflet-routing-machine';
import {environment} from "../../environments/environment";
import {Router} from "@angular/router";
import {HeaderService} from "../header/header.service";

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
  stationLocation: any;
  private map: any;
  dostupniTragaci: any;
  header = new HttpHeaders({
    'Authorization': 'Bearer ' + localStorage.getItem('token'),
    //'Content-Type': 'application/json',
  });
  headersObj = {
    headers: this.header
  }
  private defaultIcon: Icon = icon({
    iconUrl: "https://unpkg.com/leaflet@1.5.1/dist/images/marker-icon.png"
  });

  constructor(private http: HttpClient, private router: Router, private headerService: HeaderService) {
    Marker.prototype.options.icon = this.defaultIcon;
  }
  ngOnInit(): void {
    this.headerService.changeActivePage("/station-choosing")
    this.http.get<any>(environment.BASE_API_URL + "/stationLead/myStation", this.headersObj).subscribe({
      next: (data: any) => {
        this.stationName = data.stationName;
        this.stationSurface = Math.pow(data.radius, 2);

        this.http.get<any>(environment.BASE_API_URL + "/stationLead/availableExplorers", this.headersObj).subscribe({
          next: (data: any) => {
            this.dostupniTragaci = data;
          }
        })

        this.http.get<any>(environment.BASE_API_URL + "/stationLead/explorers", this.headersObj).subscribe({
          next: (data: any) => {
            this.tragaci2 = data;
            this.isTragaciEmpty = !(this.tragaci2.length > 0);
          }
        })      }
    })  // dohvacanje moje stanice
  }

  private initMap(): void {
    this.http.get<any>(environment.BASE_API_URL + "/stationLead/myStation", this.headersObj).subscribe({
      next: (data: any) => {
        let stationLoc = { lat: data.stationLocation.split(", ")[0], lng: data.stationLocation.split(", ")[1] };
        this.map = L.map('map', {
          center:  stationLoc,
          zoom: 10
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

        let circle = L.circle(stationLoc, {
          radius: data.radius * 100,
          color: 'red',
          fillColor: '#f03'
        }).addTo(this.map);
      }
    })
  }

  ngAfterViewInit(): void {
    this.initMap();
  }

  saveResearchers() {
    /*this.http.put<any>(environment.BASE_API_URL + "/stationLead/myStation/removeExplorer/" + "s" , this.stationName, this.headersObj).subscribe({
      next: (data: any) => {
      }
    })*/
    /*this.http.put<any>(environment.BASE_API_URL + "/stationLead/myStation/removeExplorer/" + "Marko" , this.stationName, this.headersObj).subscribe({
      next: (data: any) => {
      }
    })
    this.http.put<any>(environment.BASE_API_URL + "/stationLead/myStation/removeExplorer/" + "Sebo" , this.stationName, this.headersObj).subscribe({
      next: (data: any) => {
      }
    })
    this.http.put<any>(environment.BASE_API_URL + "/stationLead/myStation/removeExplorer/" + "Ivan" , this.stationName, this.headersObj).subscribe({
      next: (data: any) => {
      }
    })*/
    let i = 1;
    for (let tragac of this.tragaci.value) {
      this.http.put<any>(environment.BASE_API_URL + "/stationLead/myStation/addExplorer/" +tragac.clientName , this.stationName, this.headersObj).subscribe({
        next: (data: any) => {
          if(i == this.tragaci.value.length) {
            this.http.get<any>(environment.BASE_API_URL + "/stationLead/explorers", this.headersObj).subscribe({
              next: (data: any) => {
                this.tragaci2 = data;
                this.http.get<any>(environment.BASE_API_URL + "/stationLead/availableExplorers", this.headersObj).subscribe({
                  next: (data: any) => {
                    this.dostupniTragaci = data;
                    this.isTragaciEmpty = false;
                  }
                })
              }
            })

          }
          i++;
        }
      })
    }
  }
}
