import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Component({
  selector: 'app-researcher-profile',
  templateUrl: './researcher-profile.component.html',
  styleUrls: ['./researcher-profile.component.css']
})
export class ResearcherProfileComponent implements OnInit{
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
    this.http.get<any>(environment.BASE_API_URL + "/researcher", headersObj).subscribe({
      next: data => {
        console.log("res:",data) //ispisuje se sve osim lozinke
        let res: any = data;
        this.currentUser = {
          Name: data.firstName,
          Surname: data.lastName,
          Username: data.clientName,
          Password: data.password, //zbog toga ne mogu pristupiti lozinci
          Email: data.email,
          ClientPhoto: data.clientPhoto,
          Role: data.role,
          EducatedFor: data.educatedFor,
          Status: data.status
        }
      }
    })
  }

}
