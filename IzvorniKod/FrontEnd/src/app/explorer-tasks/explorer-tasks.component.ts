import { AfterViewInit, Component, Input, OnInit, OnDestroy } from '@angular/core';
import { HeaderService } from "../header/header.service";
import * as L from "leaflet";
import { LatLng, Marker } from "leaflet";
import { environment } from "../../environments/environment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import {MatDialog, MatDialogModule} from '@angular/material/dialog';
import { AnimalCommentsDialogComponent } from '../animal-comments-dialog/animal-comments-dialog.component';
import {FormControl, FormGroup} from "@angular/forms";

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
  comments: any
  files: any = [];
  animalForm = new FormGroup({
    animalSpecies: new FormControl(''),
    animalDescription: new FormControl('')
  })
  private markerMyLocation!: L.Marker<any>;


  customIconForMyLocation = L.icon({
    iconUrl: "assets/img/myLocation.png", 
    iconSize: [32, 32], 
    iconAnchor: [16, 32], 
    popupAnchor: [0, -32] 
  });
  customExplorerIcon = L.icon({
    iconUrl: "assets/img/ex1.png", 
    iconSize: [52, 32],
    iconAnchor: [16, 32], 
    popupAnchor: [0, -32] 
  });
  customIconAnimal = L.icon({
    iconUrl: "assets/img/animalLoc.png", 
    iconSize: [32, 32],
    iconAnchor: [16, 32], 
    popupAnchor: [0, -32] 
  });


  errorMessage: string = "";
  customIcon = L.icon({
    iconUrl: "https://unpkg.com/leaflet@1.5.1/dist/images/marker-icon.png", 
    iconSize: [22, 32], 
    iconAnchor: [16, 32], 
    popupAnchor: [0, -32] 
  });
  header = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + localStorage.getItem('token'),
  });
  headersObj = {
    headers: this.header
  };

  latitude: any;
  longitude: any;
  error: string | null = null;
  routingControl: any;

  constructor(private headerService: HeaderService, private http: HttpClient, private dialog: MatDialog) {
    Marker.prototype.options.icon = this.customIcon;

  }

  ngOnInit() {
    this.getCurrentLocation();

    this.http.get<any>(environment.BASE_API_URL + "/explorer/tasks", this.headersObj).subscribe({
      next: (data: any) => {
        console.log(data)
        this.tasks = data;
        this.tasks.map((zadatak: any) => {
          this.idOfAction = zadatak.actionId
        })
        console.log("idOfAction: " + this.idOfAction)
         if(this.idOfAction !== undefined) {
            this.getExplorers();
          }
      }
    })

    this.getAllAnimals();

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
        if (!navigator.geolocation) {
          console.log("Browser ne podrzava geolocation");
        } else {
          navigator.geolocation.getCurrentPosition(this.getPosition);
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
    
    tiles.addTo(this.map);

      this.intervalId = setInterval(() => {
        this.getAllAnimals();
        if(this.idOfAction !== undefined) {
          this.getExplorers();
        }
        if (!navigator.geolocation) {
          console.log("Browser ne podrzava geolocation");
        } else {
          navigator.geolocation.getCurrentPosition(this.getPosition);
        }
      }, 30000)
    }

    private getPosition = (position: any) => {
      console.log(position)
      const lat = position.coords.latitude;
      const long = position.coords.longitude;
      const utcDate = new Date(position.timestamp)
      const timezoneOffset = 60
      const offsetINMiliseconds = timezoneOffset * 60 * 1000;
      const CroatianDate = new Date(utcDate.getTime() + offsetINMiliseconds) 
      const formatedDate = CroatianDate.toISOString().replace('T', ' ').split('.')[0]
      console.log("Croatina time: " + formatedDate);

      const timeStamp = new Date(position.timestamp).toISOString().slice(0, 10).replace('T', ' ');
      const time = new Date(position.timestamp).toLocaleTimeString(undefined, { hour12: false });

      const fullDateTimeString = `${timeStamp} ${time}`;

      const data = {
        longitude: long,
        latitude: lat,
        locationTimestamp: formatedDate,
      };
      this.http.post(environment.BASE_API_URL + "/explorerlocation/add/" + this.currentUser.Username, data, this.headersObj).subscribe({
        next: responseData => {
        },
        error: error => {
          console.error("Error sending location:", error);
          console.log("Error Status: " + error.status);
          console.log("Error Message: " + error.message);
        }
      })
      

      if (this.markerMyLocation) {
        console.log("Uso u removanje markerea moje loacjije")
        this.map.removeLayer(this.markerMyLocation);
      }

      let popupOptions = {
        "closeButton": false
      }

      this.markerMyLocation = L.marker([lat, long], {icon:this.customIconForMyLocation}).addTo(this.map)
      .on("mouseover", event => {
        event.target.bindPopup('<h3>Moja lokacija</h3>', popupOptions).openPopup();
      })
      .on("mouseout", event => {
        event.target.closePopup();
      });
    }

  private getAllAnimals(): void {
      if(this.animalMarkersLayer) {
        this.animalMarkersLayer.clearLayers();
      }

      this.http.get<any>(environment.BASE_API_URL + "/animal/currentLocations/all", this.headersObj).subscribe({
        next: (responseData: any) => {
          responseData.map((element: {
            animalSpecies: string;
            animalId: string; latitude: any; longitude: any;
}) => {
            const serverLat = element.latitude;
          const serverLong = element.longitude;

          const animalMarker = L.marker([serverLat, serverLong], { icon: this.customIconAnimal }).on("mouseover", event => {
            event.target.bindPopup(element.animalId + ':' + element.animalSpecies).openPopup();
          }).on("click", event => {
            console.log("Element/animal: " + element.animalId)
            this.openDialog(element.animalId);
          });
          animalMarker.addTo(this.animalMarkersLayer)
          });
          this.map.addLayer(this.animalMarkersLayer);
        },
        error: error => {
          console.error("Error getting location:", error);
        }
      })
  }

  private getExplorers() {
    if(this.explorersMarkersLayer) {
      this.explorersMarkersLayer.clearLayers();
    }
    this.http.get<any>(environment.BASE_API_URL + "/action/explorers/" + this.idOfAction, this.headersObj).subscribe({
      next: (responseData: any) => {
        console.log(responseData);

        responseData.map((element: {
          explorerName: string; latitude: any; longitude: any;
}) => {
        if(element.explorerName !== this.currentUser.Username) {
          const serverLat = element.latitude;
        const serverLong = element.longitude;

        const animalMarker = L.marker([serverLat, serverLong], { icon: this.customExplorerIcon }).on("mouseover", event => {
          event.target.bindPopup(element.explorerName).openPopup();
        });
        animalMarker.addTo(this.explorersMarkersLayer)
        }
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
      console.log("Ociscen interval i maknuta karta")
    }
  }

  onTaskClick(task: any, i: any) {
    if (!task.clicked) {
      document.getElementById(i + 'button')!.style.transform = "rotate(90deg)";
      this.map.eachLayer((layer: L.Layer) => {
        if (layer instanceof L.Circle) {
          this.map.removeLayer(layer);
        }
        if(this.routingControl) {
          this.routingControl.remove();
        }
      });
      task.clicked = true;
      let [latitude, longitude] = task.endLocation.split(',').map(Number);
      let latLng = { lat: latitude, lng: longitude };
      let newCenter = latLng; 
      let newZoomLevel = 10; 
      this.map.setView(newCenter, newZoomLevel);
      if (task.taskDescription.split(': ')[0] !== 'Prođi rutom' && task.taskDescription.split(': ')[0] !== 'Dođi na lokaciju') {
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
      } else if(task.taskDescription.split(': ')[0] === 'Prođi rutom') {
        let startCoords = task.startLocation.split(',').map(parseFloat);
        let endCoords = task.endLocation.split(',').map(parseFloat);
        this.routingControl = L.Routing.control({
          waypoints: [L.latLng(startCoords[0], startCoords[1]),
          L.latLng(endCoords[0], endCoords[1])],
          routeWhileDragging: true,
          show: false
        }).addTo(this.map);
        (document.getElementById(i) as HTMLInputElement)!.disabled = false;
      } else if(task.taskDescription.split(': ')[0] === 'Dođi na lokaciju') {
        console.log(this.latitude, this.longitude);
        let endCoords = task.endLocation.split(',').map(parseFloat);
        this.routingControl = L.Routing.control({
          waypoints: [L.latLng(this.latitude, this.longitude),
            L.latLng(endCoords[0], endCoords[1])],
          routeWhileDragging: true,
          show: false
        }).addTo(this.map);
        (document.getElementById(i) as HTMLInputElement)!.disabled = false;
      }
    } else {
      let element = (document.getElementById(i + 'error'));
      element!.innerHTML = "";
      (document.getElementById(i) as HTMLInputElement)!.disabled = true;
      document.getElementById(i + 'button')!.style.transform = "rotate(0deg)";
      task.clicked = false;
      this.map.eachLayer((layer: L.Layer) => {
        if (layer instanceof L.Circle || layer instanceof L.Marker) {
          this.map.removeLayer(layer);
        }
        if(this.routingControl) {
          this.routingControl.remove();
        }
      });
      task.locationSet = "";
    }
  }

  onCheckboxChange(event: any, task: any, i: any) {
    let id = i + 'input'
    console.log(id)
    console.log((document.getElementById(String(i)) as HTMLInputElement))
    let body = (document.getElementById(i + 'input') as HTMLInputElement).value;
    let body2 = {
      animalSpecies: this.animalForm.value.animalSpecies,
      animalDescription: this.animalForm.value.animalDescription,
      animalPhotoURL: ""
    }
    if(task.taskDescription.split(': ')[0] === 'Postavi GPS' && body && body2.animalSpecies !== "" && body2.animalDescription !== "") {
      this.http.put<any>(environment.BASE_API_URL + "/task/" + task.taskId + "/comment", body, this.headersObj).subscribe();
      this.http.put<any>(environment.BASE_API_URL + "/explorer/task/" + task.taskId + "/done", "", this.headersObj).subscribe();
      this.http.post<any>(environment.BASE_API_URL + "/animal/add", body2,this.headersObj).subscribe();
      this.http.get<any>(environment.BASE_API_URL + "/explorer/tasks", this.headersObj).subscribe({
        next: (data: any) => {
          this.tasks = data;
        }
      })
    } else if(task.taskDescription.split(': ')[0] === 'Postavi GPS' && !(body && body2.animalSpecies !== "" && body2.animalDescription !== "")) {
      let element = (document.getElementById(i + 'error'));
      element!.innerHTML = "Popunite sva polja";
      (document.getElementById(i) as HTMLInputElement).checked = false;
    } else if(body && task.taskDescription.split(': ')[0] !== 'Postavi GPS') {
      this.http.put<any>(environment.BASE_API_URL + "/task/" + task.taskId + "/comment", body, this.headersObj).subscribe();
      this.http.put<any>(environment.BASE_API_URL + "/explorer/task/" + task.taskId + "/done", "", this.headersObj).subscribe();
      this.http.get<any>(environment.BASE_API_URL + "/explorer/tasks", this.headersObj).subscribe({
        next: (data: any) => {
          this.tasks = data;
        }
      })
    } else {
      (document.getElementById(i) as HTMLInputElement).checked = false;
      let element = (document.getElementById(i + 'error'));
      element!.innerHTML = "Napišite komentar";
    }
    this.refreshData();
  }

  refreshData() {
    this.ngOnInit();
    location.reload();
  }

  openDialog(animalId: any) {
    this.http.get<any>(environment.BASE_API_URL + "/animalcomment/get/" + animalId, this.headersObj).subscribe({
      next: data => {
        console.log("Getani komentari", data);
        const dataForDialog = data
        const nameOfExplorer = this.currentUser.Username

        const dialogRef = this.dialog.open(AnimalCommentsDialogComponent, {
          width: '60%',
          height: '65%',
          data: { dataForDialog, animalId, nameOfExplorer },
        });
  
        dialogRef.afterClosed().subscribe(result => {
        });
      },
      error: error => {
        console.error("Error fetching comments:", error);
      }
    });
  }
  

  handleFileInput(event: any) {
    const fileInput = event.target;
    const imagePreview = document.getElementById('imagepreview');

    if (fileInput.files && fileInput.files[0]) {
      const reader = new FileReader();

      reader.onload = function (e) {
        imagePreview!.innerHTML = `<img src="${e.target!.result}" alt="Image Preview" height="150px" width="auto">`;
      };


      reader.readAsDataURL(fileInput.files[0]);
      this.files.push(fileInput.files[0]);
    } else if(this.files[0]==undefined){
      imagePreview!.innerHTML = '';
    }
  }

  getCurrentLocation() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          this.latitude = position.coords.latitude;
          this.longitude = position.coords.longitude;
        },
        (error) => {
          this.error = error.message;
        }
      );
    } else {
      this.error = 'Geolocation is not supported by your browser.';
    }
  }

}
