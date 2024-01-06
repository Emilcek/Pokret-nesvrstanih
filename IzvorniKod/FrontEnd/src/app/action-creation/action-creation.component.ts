import { Component,AfterViewInit, OnInit} from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms'
import * as L from 'leaflet';
import 'leaflet-routing-machine';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {HeaderService} from "../header/header.service";
import {environment} from "../../environments/environment";
@Component({
  selector: 'app-action-creation',
  templateUrl: './action-creation.component.html',
  styleUrls: ['./action-creation.component.css']
})
export class ActionCreationComponent implements AfterViewInit, OnInit {
  constructor(private http:HttpClient, private router:Router, private headerService: HeaderService){}
  tasks: any = [];
  private map: any;
  private center: L.LatLngExpression = L.latLng(45.1, 15.2);
  chosenStationName: any;
  startLocation:any;
  endLocation:any;
  taskAdded: any=false;
  educatedForChosen:any;
  markersGroup:any;
  actionAdded:any=false;
  actionDone:boolean=true;

  task=new FormGroup({
    actionTitle:new FormControl(''),
    actionDescription:new FormControl(''),
    station:new FormControl('placeholder'),
    educatedFor:new FormControl('placeholder'),
    description:new FormControl(),
    taskType:new FormControl('placeholder')
  })

  ngOnInit() {
  this.actionDone=this.actionNotDone();
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
    this.markersGroup = L.layerGroup();
    this.map.addLayer(this.markersGroup);

    this.map.on('click', (e:any) => {
      // get the count of currently displayed markers
      var markersCount = this.markersGroup.getLayers().length;
      if (markersCount < 2) {
        var marker = L.marker(e.latlng,{icon:customIcon,draggable:true}).addTo(this.markersGroup);
        if(markersCount!=1){
          marker.bindPopup("Krajnja lokacija").openPopup();
          this.endLocation=e.latlng.lat+","+e.latlng.lng
          this.startLocation=null
        }else{
          marker.bindPopup("Početna lokacija").openPopup();
          this.startLocation=e.latlng.lat+","+e.latlng.lng
        }

        return;
      }
      console.log(this.startLocation)
      // remove the markers when MARKERS_MAX is reached
      this.markersGroup.clearLayers();
    });
  }

  ngAfterViewInit(): void {
    if(this.actionDone){
      this.initMap();
    }

  }

  addTask() {
    this.taskAdded=true;
    if (this.task.value.description!=null && this.task.value.educatedFor!='placeholder' && this.markersGroup.getLayers().length>0 && this.task.value.taskType!='placeholder'
    && !(this.task.value.taskType==="Prođi rutom" && this.markersGroup.getLayers().length<2)){
      this.tasks.push({"description": this.task.value.taskType+": "+this.task.value.description, "endLocation": this.endLocation, "startLocation":this.startLocation,"taskVehicle": this.educatedForChosen});
      this.markersGroup.clearLayers();
      this.taskAdded=false;
      this.task.get('description')?.reset()
      this.task.get('educatedFor')?.setValue('placeholder')
      this.task.get('taskType')?.setValue('placeholder')
    }

  }

  dismissTask(){
    console.log(this.task.value.description)
    this.taskAdded=false;
    this.markersGroup.clearLayers();
    this.task.reset();
    this.task.get('description')?.reset()
    this.task.get('educatedFor')?.setValue('placeholder')
    this.task.get('taskType')?.setValue('placeholder')
  }

  saveEducatedFor(event:any){
    this.educatedForChosen=event.target.value;
    return event.target.value;
  }

  deleteFromTasks(task:any){
    this.tasks.splice(this.tasks.indexOf(task),1)
  }

  sendAction(){
    this.actionAdded=true;
    //ovdje treba provjeriti popis akcija da se zna može li korisnik izraditi novu akciju
    if(this.task.value.actionTitle!=null && this.task.value.actionDescription!=null && this.task.value.station!='placeholder' && this.tasks.length>0){
      let formData = new FormData()
      formData.append('station',this.task.value.station!)
      formData.append('name',this.task.value.actionTitle!)
      formData.append('description',this.task.value.actionDescription!)
      formData.append('tasks',JSON.stringify(this.tasks))

      formData.forEach(function(value, key){
        console.log(key + ': ' + value);
      });

      let header = new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
        'Content-Type': 'application/json'
      });
      let headersObj = {
        headers: header
      };
      this.http.post(environment.BASE_API_URL+"/researcher/request",formData,headersObj).subscribe({
        next: data => {
          let response: any = data;
          console.log(response)
          }, error: (error) => {
          console.log(error,"error")
        }
      })
      this.actionAdded=false;
      this.task.reset()
      this.task.get('station')?.setValue('placeholder')
      this.task.get('educatedFor')?.setValue('placeholder')
      this.task.get('taskType')?.setValue('placeholder')
      this.tasks=[]
      }
    }

    actionNotDone(){
      let header = new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
      });
      let headersObj = {
        headers: header
      };
    this.http.get(environment.BASE_API_URL+"/researcher/actions",headersObj).subscribe({
    next: data => {
        let response: any = data;
        console.log(response)
        if(response.length==0) {
          this.actionDone = true;
        }
      }, error: (error) => {
        console.log(error,"error")
      }
    })

    // u ovoj funckiji provjeriti ima li istraživač već izrađenu akciju koja čeka odobrenje
      // ako ima, onda mu se prikazije ekran s porukom da već ima akciju koja čeka odobrenje
      // inače se prikazuje ekran za izradu nove akcije
      return true;
    }
    //  /stationLeads/stations za getanje stationa

  }

