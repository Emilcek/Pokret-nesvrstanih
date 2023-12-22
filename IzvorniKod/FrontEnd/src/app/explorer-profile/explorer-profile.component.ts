import {Component, OnInit} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {UserDataEditingComponent} from "../user-data-editing/user-data-editing.component";

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
  selector: 'app-explorer-profile',
  templateUrl: './explorer-profile.component.html',
  styleUrls: ['./explorer-profile.component.css']
})

export class ExplorerProfileComponent implements OnInit{
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
    this.http.get<any>(environment.BASE_API_URL + "/client", headersObj).subscribe({
      next: data => {
        console.log("This is data:",data) //ispisuje se sve osim lozinke
        let res: any = data;
        this.currentUser = {
          Name: data.firstName,
          Surname: data.lastName,
          Username: data.clientName,
          Password: data.password, //zbog toga ne mogu pristupiti lozinci
          Email: data.email,
          ClientPhoto: data.clientPhoto,
          Role: data.role==="tragac" ? "tragač":(data.role==="voditeljPostaje" ? "voditelj postaje" : "istraživač")
        }
        console.log(this.currentUser, "user")
      }
    })
  }

}
