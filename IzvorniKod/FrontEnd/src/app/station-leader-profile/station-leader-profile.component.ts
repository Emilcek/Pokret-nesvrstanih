import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Component({
  selector: 'app-station-leader-profile',
  templateUrl: './station-leader-profile.component.html',
  styleUrls: ['./station-leader-profile.component.css']
})
export class StationLeaderProfileComponent implements OnInit{
  currentUser: any;

  constructor(private http: HttpClient) {
  }
  ngOnInit() {
    let header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + localStorage.getItem('token'),
    });
    let headersObj = {
      headers: header
    };

    //backend mi ne vraća loziku
    this.http.get<any>(environment.BASE_API_URL + "/stationLead", headersObj).subscribe({
      next: data => {
        console.log("aaaaaaaaaaa:",data) //ispisuje se sve osim lozinke
        let res: any = data;
        this.currentUser = {
          Name: data.firstName,
          Surname: data.lastName,
          Username: data.clientName,
          Password: data.password,
          Email: data.email,
          ClientPhoto: data.clientPhoto,
          Role: data.role,
          EducatedFor: data.educatedFor,
          Status: data.status,
          StationName: data.stationName,
        }
      }
    })

    this.http.get<any>(environment.BASE_API_URL + "/stationLead/availableExplorers", headersObj).subscribe({
      next: data => {
        console.log("bbbbb:",data)
        }
    })
  }

}
