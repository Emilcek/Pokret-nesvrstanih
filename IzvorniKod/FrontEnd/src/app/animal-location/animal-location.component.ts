import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, AfterViewInit, OnDestroy } from '@angular/core';
import * as L from 'leaflet';
import {environment} from "../../environments/environment";
import { ActivatedRoute } from '@angular/router';
import { every } from 'rxjs';

@Component({
  selector: 'app-animal-location',
  templateUrl: './animal-location.component.html',
  styleUrls: ['./animal-location.component.css']
})
export class AnimalLocationComponent implements AfterViewInit, OnDestroy{
  private map: any;
  private id: string | undefined;
  private intervalId : any

  constructor(private http: HttpClient, private route: ActivatedRoute) {

  }


  private initMap(): void {
    this.map = L.map('mapa', {
      center: [ 45.815399, 15.966568],
      zoom: 9
    });


    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });

    const customIcon = L.icon({
      iconUrl: "assets/img/myLoc.png", 
      iconSize: [32, 32], 
      iconAnchor: [16, 32], 
      popupAnchor: [0, -32] 
    });

    let marker: L.Marker<any>, circle: L.Circle<any>;

    const getPosition = (position:any) => {
      console.log(position)
      const lat = position.coords.latitude;
      const long = position.coords.longitude;
      console.log("Staee: " + new Date(position.timestamp).toLocaleDateString())
      const timeStamp = new Date(position.timestamp).toISOString().slice(0, 10).replace('T', ' ');
      const time = new Date(position.timestamp).toLocaleTimeString(undefined, { hour12: false });

      const utcDate = new Date(position.timestamp)
      const timezoneOffset = 60
      const offsetINMiliseconds = timezoneOffset * 60 * 1000;
      const CroatianDate = new Date(utcDate.getTime() + offsetINMiliseconds)
      const formatedDate = CroatianDate.toISOString().replace('T', ' ').split('.')[0]
      console.log("Croatina time: " + formatedDate);

      const fullDateTimeString = `${timeStamp} ${time}`;

      console.log("Full DateTime String:", fullDateTimeString);


      const idFromRoute = this.route.snapshot.paramMap.get('id');
      this.id = idFromRoute !== null ? idFromRoute : undefined;
      console.log("ID: " + this.id)

      const data = {
        longitude: long,
        latitude: lat,
        timestamp: formatedDate,
      };

      console.log("Data: " + JSON.stringify(data))

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
  console.log("Error Status: " + error.status);
  console.log("Error Message: " + error.message);
        }
      })

      if(marker) {
        this.map.removeLayer(marker);
      }

      let popupOptions = {
        "closeButton":false
      }
      
      marker = L.marker([lat, long], {icon:customIcon}).addTo(this.map)
      .on("mouseover", event => {
        event.target.bindPopup('<h3>' +this.id+ '</h3>', popupOptions).openPopup();
      })
      .on("mouseout", event => {
        event.target.closePopup();
      });
      

      console.log("Lat: " + lat + " Long: " + long + " Timestamp: " + timeStamp)
    }

    if(!navigator.geolocation) {
      console.log("Browser ne podrzava geolocation");
    } else {
      this.intervalId = setInterval(() => {
        navigator.geolocation.getCurrentPosition(getPosition);
      }, 5000)
    }


    

    tiles.addTo(this.map)
  }

  ngOnDestroy(): void {
    if(this.intervalId) {
      clearInterval(this.intervalId)
      console.log("Ociscen interval i maknuta karta")
    }
  }

  ngAfterViewInit(): void {
    this.initMap();
  }

}
