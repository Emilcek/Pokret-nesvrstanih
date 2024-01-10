import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {ActivatedRoute, Router} from "@angular/router";
import {NgForOf, NgIf} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";

@Component({
  selector: 'app-action-details-leader',
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    ReactiveFormsModule
  ],
  templateUrl: './action-details-leader.component.html',
  styleUrl: './action-details-leader.component.css'
})
export class ActionDetailsLeaderComponent implements OnInit {
  allActions: any;
  id: any;
  action: any;
  getDone: boolean = false;
  tragaci: any;
  body: any = [];
  errorMessage: string = "";

  header = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + localStorage.getItem('token'),
  });
  headersObj = {
    headers: this.header
  };
  constructor(private http: HttpClient, private route: ActivatedRoute, private router: Router) {
  }
  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');

    this.http.get<any>(environment.BASE_API_URL + "/stationLead/requests", this.headersObj).subscribe({
      next: (data: any) => {
        this.allActions = data;
        this.action = this.allActions.find((action: any) => action.actionId == this.id);
        this.getDone = true;
        this.http.get<any>(environment.BASE_API_URL + "/stationLead/explorers", this.headersObj).subscribe({
          next: (data: any) => {
            this.tragaci = data;
            this.action.actionTasks.forEach((task: any) => {
              this.body.push({taskId: task.taskId, explorerName: ""})
              task.poljeTragaca = this.tragaci.filter((tragac: any) =>
                tragac.educatedFor.includes(task.vehicleName)
              );
            });
            console.log(this.body, "body")
          }
        })
      }
    })
  }

  onSelect(event: any, taskId: any) {
    (this.body.find((data: any) => data.taskId == taskId)).explorerName = event.target.value;
    console.log(this.body)
  }
  acceptAction() {
    this.body.forEach((item: any) => {
      if(item.explorerName === "") {
        this.errorMessage = "Odaberite tragača za svaki zadatak."
        return;
      }
    })

    this.http.put<any>(environment.BASE_API_URL + "/stationLead/request/" + this.id + "/accepted", this.body,  this.headersObj).subscribe({
      next: (data: any) => {
        this.router.navigate(['action-handling'])
      }
      })
    }


}
