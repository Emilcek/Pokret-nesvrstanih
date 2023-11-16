import {Component, OnInit} from '@angular/core';
import {environment} from "../../environment/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {UserDataEditingComponent} from "../user-data-editing/user-data-editing.component";

export interface Explorer {
  Ime: string;
  Prezime: number;
  Username: number;
  Email: number;
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

    this.http.get<any>(environment.BASE_API_URL + "/client", headersObj).subscribe({
      next: data => {
        console.log(data)
        let res: any = data;
        this.currentUser = {
          Ime: data.firstName,
          Prezime: data.lastName,
          Username: data.clientName,
          Email: data.email
        }
        console.log(this.currentUser, "user")
      }
    })
  }

}
