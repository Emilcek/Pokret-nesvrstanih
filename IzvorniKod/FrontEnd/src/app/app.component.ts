import { Component, OnInit } from '@angular/core';
import { environment } from '../environment/environment';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  activePage: string = "";
  title = 'myApp';
  isLoggedIn: boolean;

  constructor() {
    this.isLoggedIn = localStorage.getItem("token") !== null;
  }
  ngOnInit() {
    this.activePage = window.location.pathname;
    console.log(this.activePage);
    this.isLoggedIn = localStorage.getItem("token") !== null;
  }
}
