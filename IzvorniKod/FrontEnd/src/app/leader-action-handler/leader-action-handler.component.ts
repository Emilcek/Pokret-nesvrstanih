import {Component, OnInit} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {NgForOf, NgIf} from "@angular/common";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-leader-action-handler',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    RouterLink
  ],
  templateUrl: './leader-action-handler.component.html',
  styleUrl: './leader-action-handler.component.css'
})
export class LeaderActionHandlerComponent implements OnInit {
  allActions: any;

  header = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + localStorage.getItem('token'),
  });
  headersObj = {
    headers: this.header
  };
  constructor(private http: HttpClient) {
  }
  ngOnInit(): void {
    this.http.get<any>(environment.BASE_API_URL + "/stationLead/requests", this.headersObj).subscribe({
      next: (data: any) => {
        console.log(data)
        this.allActions = data;
      }
    })
  }


}
