import {Component, Inject, AfterViewInit, OnInit, OnDestroy} from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import * as L from 'leaflet';
import 'leaflet-routing-machine';
import {environment} from "../../environments/environment";
import {HttpHeaders,HttpClient} from "@angular/common/http";
@Component({
  selector: 'app-action-details',
  templateUrl: './action-details.component.html',
  styleUrls: ['./action-details.component.css']
})
export class ActionDetailsComponent implements OnInit,AfterViewInit,OnDestroy{

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private http : HttpClient) { }
  tasks: any = [];
  taskStatus: any = {'Ongoing': 'U tijeku', 'Done': 'Izvršen'};
  private map: any;
  private center: L.LatLngExpression = L.latLng(45.1, 15.2);
  chosenStationName: any;
  startLocation:any;
  endLocation:any;
  taskAdded: any=false;
  markersGroup:any;
  explorersData:any=[];
  animalsData:any=[];
  tiles:any;
  overlayMaps:any;
  animalLayerGroup:any;
  httpGetDone:boolean=false;
  layerControl:any;
  interval:any;
  ngOnInit(): void {
    let header = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem('token'),
      'Content-Type': 'application/json'
    });
    let headersObj = {
      headers: header
    };
    this.http.get(environment.BASE_API_URL+"/animal/currentLocations/all",headersObj).subscribe({
      next: data => {
        let response: any = data;
        this.animalsData=response;
      }, error: (error) => {
        console.log(error,"krivo dodani podaci o životinjama")
      }
    })

    this.http.get(environment.BASE_API_URL+"/action/explorers/"+this.data.actionId,headersObj).subscribe({
      next: data => {
        let response: any = data;
        response.forEach((element:any) => {
          this.explorersData.push(element);
        })
        this.explorersData=response;
        var customIcon = L.icon({
          iconUrl: "../../assets/img/myLocation.png", // Specify the path to your custom icon image
          iconSize: [35, 35], // Set the size of the icon
          iconAnchor: [16, 32], // Set the anchor point of the icon (relative to its size)
          popupAnchor: [0, -32] // Set the anchor point for popups (relative to its size)
        });
        this.markersGroup = L.layerGroup();
        this.map.addLayer(this.markersGroup);
        this.explorersData.forEach((element:any) => {
          let e = L.latLng(element.latitude, element.longitude);
          let marker =L.marker(e,{icon:customIcon})
          marker.bindPopup(element.firstName+" "+element.lastName).openPopup()
          marker.addTo(this.markersGroup);
          let Icon = L.icon({
            iconUrl: "../../assets/img/myLoc.png", // Specify the path to your custom icon image
            iconSize: [28, 28], // Set the size of the icon
            iconAnchor: [16, 32], // Set the anchor point of the icon (relative to its size)
            popupAnchor: [0, -32] // Set the anchor point for popups (relative to its size)
          });
          this.animalLayerGroup = L.layerGroup();
          this.animalsData.forEach((element:any) => {
            let e = L.latLng(element.latitude, element.longitude);
            let marker =L.marker(e,{icon:Icon})
            marker.bindPopup(element.animalId+":"+element.animalSpecies).openPopup()
            marker.addTo(this.animalLayerGroup);
          })
          this.overlayMaps = {
            "Tragači": this.markersGroup,
            "Životinje": this.animalLayerGroup
          };
          // a layer group, used here like a container for markers
          var baseMaps = {
            "OpenStreetMap": this.tiles
          }
          this.layerControl = L.control.layers(baseMaps,this.overlayMaps).addTo(this.map);
        })
      }, error: (error) => {
        console.log(error,"krivo dodani podaci o tragačima")
      }
    })
    if(this.data.actionStatus=="Accepted"){
      this.interval=setInterval(() => {
        if(this.markersGroup!=undefined && this.animalLayerGroup!=undefined && this.layerControl!=undefined){
          this.markersGroup.clearLayers();
          this.animalLayerGroup.clearLayers();
          this.layerControl.remove();
        }
        this.http.get(environment.BASE_API_URL+"/animal/currentLocations/all",headersObj).subscribe({
          next: data => {
            let response: any = data;
            this.animalsData=response;
          }, error: (error) => {
            console.log(error,"krivo dodani podaci o životinjama")
          }
        })
        this.http.get(environment.BASE_API_URL+"/action/explorers/"+this.data.actionId,headersObj).subscribe({
          next: data => {
            let response: any = data;
            response.forEach((element:any) => {
              this.explorersData.push(element);
            })
            this.explorersData=response;
            var customIcon = L.icon({
              iconUrl: "../../assets/img/myLocation.png", // Specify the path to your custom icon image
              iconSize: [35, 35], // Set the size of the icon
              iconAnchor: [16, 32], // Set the anchor point of the icon (relative to its size)
              popupAnchor: [0, -32] // Set the anchor point for popups (relative to its size)
            });
            this.markersGroup = L.layerGroup();
            this.map.addLayer(this.markersGroup);
            this.explorersData.forEach((element:any) => {
              let e = L.latLng(element.latitude, element.longitude);
              let marker =L.marker(e,{icon:customIcon})
              marker.bindPopup(element.firstName+" "+element.lastName).openPopup()
              marker.addTo(this.markersGroup);
              let Icon = L.icon({
                iconUrl: "../../assets/img/myLoc.png", // Specify the path to your custom icon image
                iconSize: [28, 28], // Set the size of the icon
                iconAnchor: [16, 32], // Set the anchor point of the icon (relative to its size)
                popupAnchor: [0, -32] // Set the anchor point for popups (relative to its size)
              });
              this.animalLayerGroup = L.layerGroup();
              this.animalsData.forEach((element:any) => {
                let e = L.latLng(element.latitude, element.longitude);
                let marker =L.marker(e,{icon:Icon})
                marker.bindPopup(element.animalId+":"+element.animalSpecies).openPopup()
                marker.addTo(this.animalLayerGroup);
              })
              this.overlayMaps = {
                "Tragači": this.markersGroup,
                "Životinje": this.animalLayerGroup
              };
              // a layer group, used here like a container for markers
              var baseMaps = {
                "OpenStreetMap": this.tiles
              }
              this.layerControl = L.control.layers(baseMaps,this.overlayMaps).addTo(this.map);
            })
          }, error: (error) => {
            console.log(error,"krivo dodani podaci o tragačima")
          }
        })
      }, 10000);
    }

  }

  ngOnDestroy(): void {
    if(this.interval){
      clearInterval(this.interval)
    }
  }

  private initMap(): void {
    this.map = L.map('map', {
      center: this.center,
      zoom: 7
    });

    this.tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright"></a>'
    });

    this.tiles.addTo(this.map);

  }
  ngAfterViewInit(): void {
      this.initMap();


  }
}
