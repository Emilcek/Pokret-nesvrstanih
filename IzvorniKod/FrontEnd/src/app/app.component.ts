import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  activePage: string = "";
  title = 'myApp';

  ngOnInit() {
    this.activePage = window.location.pathname;
    console.log(this.activePage)
  }
}
