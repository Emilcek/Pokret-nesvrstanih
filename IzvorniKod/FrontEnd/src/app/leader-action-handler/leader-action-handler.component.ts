import {Component, OnInit} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {NgForOf, NgIf} from "@angular/common";
import {Router, RouterLink} from "@angular/router";
import {HeaderService} from "../header/header.service";

@Component({
  selector: 'app-leader-action-handler',
  templateUrl: './leader-action-handler.component.html',
  styleUrls: ['./leader-action-handler.component.css']
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
  constructor(private http: HttpClient, private router: Router, private headerService: HeaderService) {
  }
  ngOnInit(): void {
    this.headerService.changeActivePage("/action-handling")
    this.http.get<any>(environment.BASE_API_URL + "/stationLead/requests", this.headersObj).subscribe({
      next: (data: any) => {
        console.log(data)
        this.allActions = data;
        this.allActions.forEach((action: any) => {
          if(action.actionStatus === "Pending") {
            action.actionStatus = "Na čekanju"
          } else if(action.actionStatus === "Accepted") {
            action.actionStatus = "Na čekanju"
          } else {
            action.actionStatus = "Odbijena"
          }
        })
      }
    })
  }

  openDetailsPage(actionId: any) {
    this.router.navigate(['/action-details/' + actionId])
  }


}
