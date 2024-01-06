import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, AfterViewInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import * as L from 'leaflet';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-animal-location',
  templateUrl: './animal-location.component.html',
  styleUrls: ['./animal-location.component.css']
})
export class AnimalLocationComponent {
  private map: any;

  constructor(private http: HttpClient) {

  }


  private initMap(): void {
    this.map = L.map('map', {
      center: [ 45.815399, 15.966568],
      zoom: 9
    });


    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });

    let marker: L.Marker<any>, circle: L.Circle<any>;

    const getPosition = (position:any) => {
      console.log(position)
      const lat = position.coords.latitude;
      const long = position.coords.longitude;
      const timeStamp = position.timestamp;
      const accuracy = position.coords.accuracy;

      const data = {
        latitude: lat,
        longitude: long,
        timestamp: timeStamp,
      };

      //post rekvest u bazu za lat, long, tmiestamp
      let header = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
      });
      let headersObj = {
        headers: header
      };
      this.http.post(environment.BASE_API_URL + "/current/location/1", data, headersObj).subscribe({
        next: responseData => {
          console.log("Poslana lokacija: " + responseData);
        },
        error: error => {
          console.error("Error sending location:", error);
        }
      })

      //getrekvest u drugoj komponenti

      if(marker) {
        this.map.removeLayer(marker);
      }

      if(circle) {
        this.map.removeLayer(circle);
      }
      
      circle = L.circle([lat, long], {radius: accuracy}).addTo(this.map);
      marker = L.marker([lat, long]).addTo(this.map);
      

      console.log("Lat: " + lat + " Long: " + long + " Timestamp: " + timeStamp)
    }

    if(!navigator.geolocation) {
      console.log("Browser ne podrzava geolocation");
    } else {
      setInterval(() => {
        navigator.geolocation.getCurrentPosition(getPosition);
      }, 5000)
    }


    

    tiles.addTo(this.map)
  }


  ngAfterViewInit(): void {
    this.initMap();
  }

}
