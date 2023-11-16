import {Component, OnInit} from '@angular/core';
import { Input } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Router } from '@angular/router';
import {environment} from "../../environments/environment";

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent implements OnInit {
  userlist: any
  dataSource: any

  constructor(private http: HttpClient, private router: Router) {
  }

  ngOnInit() {
    let header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + localStorage.getItem('token'),
    });
    let headersObj = {
      headers: header
    };
    this.http.get<any>(environment.BASE_API_URL + "/admin/clients", headersObj).subscribe({
      next: data => {
        console.log(data)
        let res: any = data;
        this.dataSource = res;
        console.log(data, "data")
      }
    })
  }

  apiurl = environment.BASE_API_URL + '/explorer';

  LoadUser() {
    this.GetAll().subscribe(res => {
      this.dataSource = res;
      console.log(this.dataSource);
    });
  }

  GetAll() {
    return this.http.get(this.apiurl)
  }

  redirectToEditPage(userId: string) {
    //this.router.navigate([`admin/userdata/edit/${userId}`]);
  }

}
