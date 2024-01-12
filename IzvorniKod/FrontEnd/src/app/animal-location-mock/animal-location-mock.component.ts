import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, AfterViewInit, OnDestroy } from '@angular/core';
import * as L from 'leaflet';
import {environment} from "../../environments/environment";
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-animal-location-mock',
  templateUrl: './animal-location-mock.component.html',
  styleUrls: ['./animal-location-mock.component.css']
})
export class AnimalLocationMockComponent implements AfterViewInit, OnDestroy{

  private map: any;
  private marker: L.Marker<any> | undefined;
  private circle: L.Circle<any> | undefined;
  private id: string | undefined;
  private intervalId: any;

  constructor(private http: HttpClient, private route: ActivatedRoute) {

  }


  private initMap(): void {
    this.map = L.map('maps', {
      center: [45.815399, 15.966568],
      zoom: 9
    });

    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });

    tiles.addTo(this.map)
  }


    private getLocationData(): void {

      const idFromRoute = this.route.snapshot.paramMap.get('id');
      this.id = idFromRoute !== null ? idFromRoute : undefined;
      console.log("ID: " + this.id)

      const customIcon = L.icon({
        iconUrl: "https://unpkg.com/leaflet@1.5.1/dist/images/marker-icon.png", // Specify the path to your custom icon image
        iconSize: [22, 32], // Set the size of the icon
        iconAnchor: [16, 32], // Set the anchor point of the icon (relative to its size)
        popupAnchor: [0, -32] // Set the anchor point for popups (relative to its size)
      });

      //post rekvest u bazu za lat, long, tmiestamp
      let header = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
      });
      let headersObj = {
        headers: header
      };
      this.http.get<LocationResponse>(environment.BASE_API_URL + "/animal/location/get/" + this.id, headersObj).subscribe({
        next: (responseData: LocationResponse) => {
          console.log(responseData);
          const serverLat = responseData.latitude;
          const serverLong = responseData.longitude;

          // Display location on the map
          if (this.marker) {
            this.map.removeLayer(this.marker);
          }

          this.marker = L.marker([serverLat, serverLong], {icon:customIcon}).addTo(this.map);
        },
        error: error => {
          console.error("Error getting location:", error);
        }
      })
    }

    /*if(!navigator.geolocation) {
      console.log("Browser ne podrzava geolocation");
    } else {
      setInterval(() => {
        navigator.geolocation.getCurrentPosition(getPosition);
      }, 5000)
    }*/


  ngOnDestroy(): void {
    if(this.intervalId) {
      clearInterval(this.intervalId)
      this.map.remove()
      console.log("Ociscen interval i maknuta karta")
    }
  }



  ngAfterViewInit(): void {
    this.initMap();

    this.intervalId = setInterval(() => {
      this.getLocationData();
    }, 5000);
  }

}

interface LocationResponse {
  latitude: number;
  longitude: number;
  // Add other properties if needed
}


