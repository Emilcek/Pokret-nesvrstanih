import { Component, OnInit } from '@angular/core';
import {environment} from "../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  activePage: string = "";
  title = 'myApp';
  isLoggedIn: boolean;
  user: string = "";
  status: string = "";
  header = new HttpHeaders({
    'Authorization': 'Bearer ' + localStorage.getItem('token'),
  });
  headersObj = {
    headers: this.header
  };

  constructor(private http: HttpClient) {
    this.isLoggedIn = localStorage.getItem("token") !== null;
    if(localStorage.getItem("user") !== null) {
      // @ts-ignore
      this.user = localStorage.getItem("user");
      this.http.get<any>(environment.BASE_API_URL + "/client", this.headersObj).subscribe({
        next: (data: any) => {
          localStorage.setItem("status", data.status);
          this.status = data.status;
        }})
    } else {
      this.user = "";
    }
  }
  ngOnInit() {
    //localStorage.clear();
    this.activePage = window.location.pathname;
    this.isLoggedIn = localStorage.getItem("token") !== null;
    if(localStorage.getItem("user") !== null) {
      // @ts-ignore
      this.user = localStorage.getItem("user");
      this.http.get<any>(environment.BASE_API_URL + "/client", this.headersObj).subscribe({
        next: (data: any) => {
          localStorage.setItem("status", data.status);
          this.status = data.status;
        }
      })
    } else {
      this.user = "";
    }
  }
}
