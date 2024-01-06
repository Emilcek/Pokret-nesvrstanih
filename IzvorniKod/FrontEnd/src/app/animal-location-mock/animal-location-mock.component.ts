import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, AfterViewInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import * as L from 'leaflet';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-animal-location-mock',
  templateUrl: './animal-location-mock.component.html',
  styleUrls: ['./animal-location-mock.component.css']
})
export class AnimalLocationMockComponent {

  private map: any;
  private marker: L.Marker<any> | undefined;
  private circle: L.Circle<any> | undefined;

  constructor(private http: HttpClient) {

  }


  private initMap(): void {
    this.map = L.map('map', {
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
      //post rekvest u bazu za lat, long, tmiestamp
      let header = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
      });
      let headersObj = {
        headers: header
      };
      this.http.get<LocationResponse>(environment.BASE_API_URL + "/location/1", headersObj).subscribe({
        next: (responseData: LocationResponse) => {
          console.log(responseData);
          const serverLat = responseData.latitude;
          const serverLong = responseData.longitude;

          // Display location on the map
          if (this.marker) {
            this.map.removeLayer(this.marker);
          }

          this.marker = L.marker([serverLat, serverLong]).addTo(this.map);
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



  ngAfterViewInit(): void {
    this.initMap();

    setInterval(() => {
      this.getLocationData();
    }, 5000);
  }

}

interface LocationResponse {
  latitude: number;
  longitude: number;
  // Add other properties if needed
}

