import { AfterViewInit, Component, Input, OnInit, OnDestroy } from '@angular/core';
import { HeaderService } from "../header/header.service";
import * as L from "leaflet";
import { LatLng, Marker } from "leaflet";
import { environment } from "../../environments/environment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import {MatDialog, MatDialogModule} from '@angular/material/dialog';
import { AnimalCommentsDialogComponent } from '../animal-comments-dialog/animal-comments-dialog.component';

interface Task {
  type: string,
  description: string,
  location: LatLng
}

export interface Explorer {
  Name: string;
  Surname: string;
  Username: string;
  Password: string;
  Email: string;
  Role: string;
  ClientPhoto: any;
}


@Component({
  selector: 'app-explorer-tasks',
  templateUrl: './explorer-tasks.component.html',
  styleUrls: ['./explorer-tasks.component.css']
})
export class ExplorerTasksComponent implements OnInit, AfterViewInit, OnDestroy {
  tasks: any;
  private map: any;
  private intervalId: any
  currentUser: any;
  private animalMarkersLayer: any = L.layerGroup();
  private explorersMarkersLayer: any = L.layerGroup();
  idOfAction: any;

  customIconForMyLocation = L.icon({
    iconUrl: "assets/img/myLocation.png", // Specify the path to your custom icon image
    iconSize: [32, 32], // Set the size of the icon
    iconAnchor: [16, 32], // Set the anchor point of the icon (relative to its size)
    popupAnchor: [0, -32] // Set the anchor point for popups (relative to its size)
  });
  customExplorerIcon = L.icon({
    iconUrl: "assets/img/ex1.png", // Specify the path to your custom icon image
    iconSize: [52, 42], // Set the size of the icon
    iconAnchor: [16, 32], // Set the anchor point of the icon (relative to its size)
    popupAnchor: [0, -32] // Set the anchor point for popups (relative to its size)
  });
  customIconAnimal = L.icon({
    iconUrl: "assets/img/animalLoc.png", // Specify the path to your custom icon image
    iconSize: [32, 32], // Set the size of the icon
    iconAnchor: [16, 32], // Set the anchor point of the icon (relative to its size)
    popupAnchor: [0, -32] // Set the anchor point for popups (relative to its size)
  });

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

  constructor(private headerService: HeaderService, private http: HttpClient, private dialog: MatDialog) {
    Marker.prototype.options.icon = this.customIcon;

  }

  ngOnInit() {

    this.http.get<any>(environment.BASE_API_URL + "/explorer/tasks", this.headersObj).subscribe({
      next: (data: any) => {
        console.log(data)
        this.tasks = data;
        this.tasks.map((zadatak: any) => {
          this.idOfAction = zadatak.actionId
        })
        console.log("idOfAction: " + this.idOfAction)
      }
    })

    this.http.get<any>(environment.BASE_API_URL + "/client", this.headersObj).subscribe({
      next: data => {
        console.log("This is data:", data)
        let res: any = data;
        this.currentUser = {
          Name: data.firstName,
          Surname: data.lastName,
          Username: data.clientName,
          Password: data.password, 
          Email: data.email,
          ClientPhoto: data.clientPhoto,
          Role: data.role,
          EducatedFor: data.educatedFor
        }
      }
    })
  }

  private initMap(): void {
    this.map = L.map('map', {
      center: [45.1, 15.2],
      zoom: 7
    });
    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });

    let markerMyLocation: L.Marker<any>;

    const getPosition = (position: any) => {

      console.log(position)
      const lat = position.coords.latitude;
      const long = position.coords.longitude;
      const timeStamp = new Date(position.timestamp).toISOString().slice(0, 10).replace('T', ' ');
      const time = new Date(position.timestamp).toLocaleTimeString(undefined, { hour12: false });

      const fullDateTimeString = `${timeStamp} ${time}`;

      console.log("Full DateTime String:", fullDateTimeString);

      console.log("Username: " + this.currentUser.Username)
      const data = {
        longitude: long,
        latitude: lat,
        locationTimestamp: fullDateTimeString,
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
      this.http.post(environment.BASE_API_URL + "/explorerlocation/add/" + this.currentUser.Username, data, headersObj).subscribe({
        next: responseData => {
          console.log("Poslana lokacija: " + responseData);
        },
        error: error => {
          console.error("Error sending location:", error);
          console.log("Error Status: " + error.status);
          console.log("Error Message: " + error.message);
        }
      })

      //getrekvest u drugoj komponenti

      if (markerMyLocation) {
        this.map.removeLayer(markerMyLocation);
      }

      let popupOptions = {
        "closeButton": false
      }

      //circle = L.circle([lat, long], {radius: accuracy}).addTo(this.map);
      markerMyLocation = L.marker([lat, long], { icon: this.customIconForMyLocation }).addTo(this.map)
        .on("mouseover", event => {
          event.target.bindPopup('<h3>Moja lokacija</h3>', popupOptions).openPopup();
        })
        .on("mouseout", event => {
          event.target.closePopup();
        });
      console.log("Lat: " + lat + " Long: " + long + " Timestamp: " + timeStamp)
    }

    tiles.addTo(this.map);



    //moj dio za prikaz trenutne lokacije 
      this.intervalId = setInterval(() => {
        if (!navigator.geolocation) {
          console.log("Browser ne podrzava geolocation");
        } else {
          navigator.geolocation.getCurrentPosition(getPosition);
          this.getAllAnimals();
          this.getExplorers();
        }
      }, 5000)
    }

  private getAllAnimals(): void {
    console.log("Get animals")

      console.log("Prije removanja: " + this.animalMarkersLayer.getLayers().length)

      if(this.animalMarkersLayer) {
        this.animalMarkersLayer.clearLayers();
        console.log("Poslije removanja: " + this.animalMarkersLayer.getLayers().length)
      }


      let header = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
      });
      let headersObj = {
        headers: header
      };
      this.http.get<any>(environment.BASE_API_URL + "/animal/currentLocations/all", headersObj).subscribe({
        next: (responseData: any) => {
          console.log(responseData);

          responseData.map((element: {
            animalSpecies: string;
            animalId: string; latitude: any; longitude: any; 
}) => {
            const serverLat = element.latitude;
          const serverLong = element.longitude;

          const animalMarker = L.marker([serverLat, serverLong], { icon: this.customIconAnimal }).on("mouseover", event => {
            event.target.bindPopup(element.animalId + ':' + element.animalSpecies).openPopup();
          }).on("click", event => {
            console.log("ID i vrsta: " + element.animalId + ", " + element.animalSpecies)
            //get rekvest za svim komentarima vezanim uz tu zivotinju po ID-u zivotinje
            this.openDialog(element);
          })
          ;
          console.log("Die zivina: " + serverLat + ", " + serverLong)
          animalMarker.addTo(this.animalMarkersLayer)
          });
          console.log("Layeri: " + this.animalMarkersLayer.getLayers().length)
          this.map.addLayer(this.animalMarkersLayer);
        },
        error: error => {
          console.error("Error getting location:", error);
        }
      }) 
  }

  private getExplorers() {
    console.log("Get explorers")

    console.log("Prije removanja: " + this.explorersMarkersLayer.getLayers().length)

    if(this.explorersMarkersLayer) {
      this.explorersMarkersLayer.clearLayers();
      console.log("Poslije removanja: " + this.explorersMarkersLayer.getLayers().length)
    }


    let header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + localStorage.getItem('token'),
    });
    let headersObj = {
      headers: header
    };
    this.http.get<any>(environment.BASE_API_URL + "/action/explorers/" + this.idOfAction, headersObj).subscribe({
      next: (responseData: any) => {
        console.log(responseData);

        responseData.map((element: {
          explorerName: string; latitude: any; longitude: any; 
}) => {
          const serverLat = element.latitude;
        const serverLong = element.longitude;

        const animalMarker = L.marker([serverLat, serverLong], { icon: this.customExplorerIcon }).on("mouseover", event => {
          event.target.bindPopup(element.explorerName).openPopup();
        });
        console.log("Die explorer: " + serverLat + ", " + serverLong)
        animalMarker.addTo(this.explorersMarkersLayer)
        });
        console.log("Layeri: " + this.explorersMarkersLayer.getLayers().length)
        this.map.addLayer(this.explorersMarkersLayer);
      },
      error: error => {
        console.error("Error getting location:", error);
      }
    }) 
  }

  ngAfterViewInit(): void {
    this.initMap();
  }

  ngOnDestroy(): void {
    if (this.intervalId) {
      clearInterval(this.intervalId)
      //this.map.remove()
      console.log("Ociscen interval i maknuta karta")
    }
  }

  onTaskClick(task: any, i: any) {
    if (!task.clicked) {
      task.clicked = true;
      let [latitude, longitude] = task.endLocation.split(',').map(Number);
      let latLng = { lat: latitude, lng: longitude };
      let newCenter = latLng; // Replace latitude and longitude with your new center coordinates
      let newZoomLevel = 10; // Replace with the desired zoom level
      this.map.setView(newCenter, newZoomLevel);
      if (task.taskDescription.split(': ')[0] !== 'Prođi rutom') {
        let circle = L.circle(latLng, {
          radius: 5000,
          color: 'red',
          fillColor: '#f03'
        }).addTo(this.map);

        this.map.on('click', (e: any) => {
          if (task.locationSet === null || task.locationSet === undefined) {
            let marker = L.marker(e.latlng, { icon: this.customIcon, draggable: true })
            marker.addTo(this.map)
            let lat = Math.round(e.latlng.lat * 100) / 100;
            let lng = Math.round(e.latlng.lng * 100) / 100
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
    this.http.put<any>(environment.BASE_API_URL + "/task/" + task.taskId + "/comment", body, this.headersObj).subscribe({
      next: data => {

        this.http.put<any>(environment.BASE_API_URL + "/explorer/task/" + task.taskId + "/done", "", this.headersObj).subscribe({
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

  openDialog(animal: any) {
    const dialogRef = this.dialog.open(AnimalCommentsDialogComponent, {
      width:'60%',
      height:'65%',
      data: animal,
    });
    //console.log(user)
    dialogRef.afterClosed().subscribe(result => {
      
    });
  }

}
