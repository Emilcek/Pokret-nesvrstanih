import {AfterViewInit, Component, OnInit} from '@angular/core';
import {HeaderService} from "../header/header.service";
import * as L from "leaflet";
import {LatLng, Marker} from "leaflet";
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";

interface Task {
  type: string,
  description: string,
  location: LatLng
}

@Component({
  selector: 'app-explorer-tasks',
  templateUrl: './explorer-tasks.component.html',
  styleUrls: ['./explorer-tasks.component.css']
})
export class ExplorerTasksComponent implements OnInit, AfterViewInit {
  tasks: any;
  private map: any;
  errorMessage: string = "";
  customIcon = L.icon({
    iconUrl: "https://unpkg.com/leaflet@1.5.1/dist/images/marker-icon.png", // Specify the path to your custom icon image
    iconSize: [22, 32], // Set the size of the icon
    iconAnchor: [16, 32], // Set the anchor point of the icon (relative to its size)
    popupAnchor: [0, -32] // Set the anchor point for popups (relative to its size)
  });
  header = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + localStorage.getItem('token'),
  });
  headersObj = {
    headers: this.header
  };

  constructor(private headerService: HeaderService, private http: HttpClient) {
    Marker.prototype.options.icon = this.customIcon;

  }

  ngOnInit() {

    this.http.get<any>(environment.BASE_API_URL + "/explorer/tasks", this.headersObj).subscribe({
      next: (data: any) => {
        console.log(data)
        this.tasks = data;
      }
    })
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

    tiles.addTo(this.map);
  }

  ngAfterViewInit(): void {
    this.initMap();
  }

  onTaskClick(task: any, i: any) {
    if(!task.clicked) {
      task.clicked = true;
      let [latitude, longitude] = task.endLocation.split(',').map(Number);
      let latLng = { lat: latitude, lng: longitude };
      let newCenter = latLng; // Replace latitude and longitude with your new center coordinates
      let newZoomLevel = 10; // Replace with the desired zoom level
      this.map.setView(newCenter,newZoomLevel);
      if(task.taskDescription.split(': ')[0] !== 'Prođi rutom') {
        let circle = L.circle(latLng, {
          radius: 5000,
          color: 'red',
          fillColor: '#f03'
        }).addTo(this.map);

        this.map.on('click', (e: any) => {
          if(task.locationSet === null || task.locationSet === undefined) {
            let marker = L.marker(e.latlng, {icon: this.customIcon, draggable: true})
            marker.addTo(this.map)
            let lat = Math.round(e.latlng.lat * 100 ) / 100;
            let lng = Math.round(e.latlng.lng * 100 ) / 100
            task.locationSet = lat + ',' + lng;
            (document.getElementById(i) as HTMLInputElement)!.disabled = false;
          }
        })
      } else {
        let startCoords = task.startLocation.split(',').map(parseFloat);
        let endCoords = task.endLocation.split(',').map(parseFloat);
        L.Routing.control({
          waypoints: [L.latLng(startCoords[0], startCoords[1]),
          L.latLng(endCoords[0], endCoords[1])],
          routeWhileDragging: true,
          show: false
        }).addTo(this.map);
        (document.getElementById(i) as HTMLInputElement)!.disabled = false;
      }
    }
  }

  onCheckboxChange(event: any, task: any, i: any) {
    let id = i + 'input'
    let body = (document.getElementById(id) as HTMLInputElement).value;
    this.http.put<any>(environment.BASE_API_URL + "/task/" + task.taskId + "/comment", body,  this.headersObj).subscribe({
      next: data => {

        this.http.put<any>(environment.BASE_API_URL + "/explorer/task/" + task.taskId + "/done","", this.headersObj).subscribe({
          next: (data: any) => {

            this.http.get<any>(environment.BASE_API_URL + "/explorer/tasks", this.headersObj).subscribe({
              next: (data: any) => {
                console.log(data)
                this.tasks = data;
              }
            })
          }
        })
      }, error: error => {
        this.errorMessage = "Napišite komentar";
        (document.getElementById(i) as HTMLInputElement).checked = false;
      }
    })
  }

}
