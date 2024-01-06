import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, AfterViewInit } from '@angular/core';
import * as L from 'leaflet';
import {environment} from "../../environments/environment";
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-animal-location',
  templateUrl: './animal-location.component.html',
  styleUrls: ['./animal-location.component.css']
})
export class AnimalLocationComponent {
  private map: any;
  private id: string | undefined;

  constructor(private http: HttpClient, private route: ActivatedRoute) {

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
      const timeStamp = new Date(position.timestamp).toISOString().slice(0, 19).replace('T', ' ');
      console.log("Timestamp: " + timeStamp)
      const accuracy = position.coords.accuracy;

      const idFromRoute = this.route.snapshot.paramMap.get('id');
      this.id = idFromRoute !== null ? idFromRoute : undefined;
      console.log("ID: " + this.id)

      const data = {
        longitude: long,
        latitude: lat,
        timestamp: timeStamp,
      };

      console.log("Data: " + JSON.stringify(data))

      //post rekvest u bazu za lat, long, tmiestamp
      let header = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
      });
      let headersObj = {
        headers: header
      };
      this.http.post(environment.BASE_API_URL + "/animallocation/" + this.id, data, headersObj).subscribe({
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
