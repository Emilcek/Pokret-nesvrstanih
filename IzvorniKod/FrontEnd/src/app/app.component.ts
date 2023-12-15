import { Component, OnInit } from '@angular/core';

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

  constructor() {
    this.isLoggedIn = localStorage.getItem("token") !== null;
    console.log(this.isLoggedIn)
    if(localStorage.getItem("user") !== null) {
      // @ts-ignore
      this.user = localStorage.getItem("user");
    } else {
      this.user = "";
    }
  }
  ngOnInit() {
    this.activePage = window.location.pathname;
    console.log(this.activePage);
    this.isLoggedIn = localStorage.getItem("token") !== null;
    if(localStorage.getItem("user") !== null) {
      // @ts-ignore
      this.user = localStorage.getItem("user");
    } else {
      this.user = "";
    }
  }
}
