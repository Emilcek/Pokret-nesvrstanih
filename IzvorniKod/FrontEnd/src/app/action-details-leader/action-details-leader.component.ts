import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {ActivatedRoute, Router} from "@angular/router";
import * as L from "leaflet";
import {icon, Icon, Marker} from "leaflet";
import 'leaflet-routing-machine';
import {HeaderService} from "../header/header.service";

@Component({
  selector: 'app-action-details-leader',
  templateUrl: './action-details-leader.component.html',
  styleUrls: ['./action-details-leader.component.css']
})
export class ActionDetailsLeaderComponent implements OnInit {
  allActions: any;
  id: any;
  action: any;
  getDone: boolean = false;
  tragaci: any;
  body: any = [];
  errorMessage: string = "";
  noResearchersError: string = "";
  checkResearchers: boolean = true;
  routingControl: any;

  header = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + localStorage.getItem('token'),
  });
  headersObj = {
    headers: this.header
  };
  private map: any;

  private defaultIcon: Icon = icon({
    iconUrl: "https://unpkg.com/leaflet@1.5.1/dist/images/marker-icon.png"
  });
  constructor(private http: HttpClient, private route: ActivatedRoute, private router: Router, private headerService: HeaderService) {
    Marker.prototype.options.icon = this.defaultIcon;

  }
  ngOnInit(): void {
    this.headerService.changeActivePage("/action-handling")
    this.id = this.route.snapshot.paramMap.get('id');

    this.http.get<any>(environment.BASE_API_URL + "/stationLead/requests", this.headersObj).subscribe({
      next: (data: any) => {
        this.allActions = data;
        this.action = this.allActions.find((action: any) => action.actionId == this.id);
        this.getDone = true;
        this.http.get<any>(environment.BASE_API_URL + "/stationLead/explorers", this.headersObj).subscribe({
          next: (data: any) => {
            this.tragaci = data;
            this.action.actionTasks.forEach((task: any) => {
              task.taskType = task.taskDescription.split(": ")[0];
              task.desc = task.taskDescription.split(": ")[1];
              this.body.push({taskId: task.taskId, explorerName: ""})
              task.poljeTragaca = this.tragaci.filter((tragac: any) =>
                tragac.educatedFor.includes(task.vehicleName)
              );
              if(task.poljeTragaca.length === 0) {
                this.checkResearchers = false;
              }
            });
            console.log(this.body, "body")
            console.log(document.getElementById("map"))
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
        })
      }
    })
  }

  onSelect(event: any, taskId: any) {
    (this.body.find((data: any) => data.taskId == taskId)).explorerName = event.target.value;
    console.log(this.body)
  }
  acceptAction() {
    if(this.checkResearchers) {
      this.body.forEach((item: any) => {
        if(item.explorerName === "") {
          this.errorMessage = "Odaberite tragača za svaki zadatak."
          return;
        }
      })

      this.http.put<any>(environment.BASE_API_URL + "/stationLead/request/" + this.id + "/accepted", this.body,  this.headersObj).subscribe({
        next: (data: any) => {
          this.router.navigate(['action-handling'])
        }
      })
    } else {
      this.noResearchersError = "Ne možete prihvatiti ovu akciju jer nemate dovoljno tragača."
    }
  }

  declineAction() {
    this.http.put<any>(environment.BASE_API_URL + "/stationLead/request/" + this.id + "/declined", this.body,  this.headersObj).subscribe({
      next: (data: any) => {
        this.router.navigate(['action-handling'])
      }
    })
  }

  showTaskOnMap(task: any) {
    this.map.eachLayer((layer: L.Layer) => {
      // Check if the layer is not the base map
      if (layer instanceof L.Circle || layer instanceof L.Routing.Control) {
        this.map.removeLayer(layer);
      }
      if(this.routingControl) {
        this.routingControl.remove();
      }
    });
    console.log(task)
    if(task.startLocation === null) {
      let [latitude, longitude] = task.endLocation.split(',').map(Number);
      let newCenter = {lat: latitude, lng: longitude}; // Replace latitude and longitude with your new center coordinates
      let newZoomLevel = 11; // Replace with the desired zoom level
      this.map.setView(newCenter,newZoomLevel);
      let circle = L.circle(newCenter, {
        radius: 5000,
        color: 'red',
        fillColor: '#f03'
      }).addTo(this.map);
    } else {
      let startCoords = task.startLocation.split(',').map(parseFloat);
      let endCoords = task.endLocation.split(',').map(parseFloat);
      this.routingControl = L.Routing.control({
        waypoints: [L.latLng(startCoords[0], startCoords[1]),
          L.latLng(endCoords[0], endCoords[1])],
        routeWhileDragging: true,
        show: false
      }).addTo(this.map);
    }
  }
}
