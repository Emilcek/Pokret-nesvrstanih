import {Component, OnInit} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Component({
  selector: 'app-leader-action-handler',
  standalone: true,
  imports: [],
  templateUrl: './leader-action-handler.component.html',
  styleUrl: './leader-action-handler.component.css'
})
export class LeaderActionHandlerComponent implements OnInit {
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
      }
    })
  }


}
