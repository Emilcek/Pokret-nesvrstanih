import { Component,AfterViewInit, OnInit} from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms'
import * as L from 'leaflet';
import 'leaflet-routing-machine';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {icon, Icon, Marker} from "leaflet";
import * as polyline from 'polyline';
@Component({
  selector: 'app-action-creation',
  templateUrl: './action-creation.component.html',
  styleUrls: ['./action-creation.component.css']
})
export class ActionCreationComponent implements AfterViewInit, OnInit {
  tasks: any = [];
  private map: any;
  private center: L.LatLngExpression = L.latLng(45.1, 15.2);
  isStationChosen: boolean = false;
  chosenStationName: any;
  chosenStationSurface: any;
  startLocation:string="";
  endLocation:string="";
  markersGroup: any;
  educatedForChosen:any;
  task=new FormGroup({
    station:new FormControl(''),
    educatedFor:new FormControl(),
    description:new FormControl('',Validators.required),
  })
  ngOnInit() {
  }

  private initMap(): void {
    this.map = L.map('map', {
      center: this.center,
      zoom: 7
    });


    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright"></a>'
    });


    var customIcon = L.icon({
      iconUrl: "https://unpkg.com/leaflet@1.5.1/dist/images/marker-icon.png", // Specify the path to your custom icon image
      iconSize: [22, 32], // Set the size of the icon
      iconAnchor: [16, 32], // Set the anchor point of the icon (relative to its size)
      popupAnchor: [0, -32] // Set the anchor point for popups (relative to its size)
    });

    var baseMaps = {
      "OpenStreetMap": tiles
    }

    tiles.addTo(this.map);

    // a layer group, used here like a container for markers
     var mg = L.layerGroup();
    this.map.addLayer(mg);

    this.map.on('click', (e:any) => {
      // get the count of currently displayed markers
      var markersCount = mg.getLayers().length;
      if (markersCount < 2) {
        var marker = L.marker(e.latlng,{icon:customIcon}).addTo(mg);
        if(markersCount!=1){
          marker.bindPopup("Početna lokacija").openPopup();
          this.startLocation=e.latlng.lat+","+e.latlng.lng
        }else{
          marker.bindPopup("Krajnja lokacija").openPopup();
          this.endLocation=e.latlng.lat+","+e.latlng.lng
        }

        return;
      }
      console.log(this.startLocation)
      // remove the markers when MARKERS_MAX is reached
      mg.clearLayers();
    });
    console.log(this.startLocation)

  }

  ngAfterViewInit(): void {
    this.initMap();
  }

  addTask() {
    if (this.task.valid && this.educatedForChosen!=undefined) {
      this.tasks.push({"description": this.task.value.description, "endLocation": this.endLocation, "startLocation":this.startLocation,"educatedFor": this.educatedForChosen});
    }
    //console.log(this.tasks)
  }

  saveEducatedFor(event:any){
    console.log(event.target.value)
    console.log(this.startLocation+","+this.endLocation)
    this.educatedForChosen=event.target.value;
    return event.target.value;
  }

  deleteFromTasks(task:any){
    this.tasks.splice(this.tasks.indexOf(task),1)
  }
}

