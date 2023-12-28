import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {environment} from "../../environments/environment";
import {MatDialog, MatDialogModule} from '@angular/material/dialog';
import {MatButtonModule} from '@angular/material/button';
import { UserRequestDialogComponent } from '../user-request-dialog/user-request-dialog.component';

@Component({
  selector: 'app-users-requests-list',
  templateUrl: './users-requests-list.component.html',
  styleUrls: ['./users-requests-list.component.css']
})
export class UsersRequestsListComponent implements OnInit {
  userlist: any
  dataSource: any

  LoadData() {
    let header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + localStorage.getItem('token'),
    });
    let headersObj = {
      headers: header
    };
    this.http.get<any>(environment.BASE_API_URL + "/admin/requests", headersObj).subscribe({
      next: data => {
        console.log(data)
        let res: any = data;
        this.dataSource = res;
        console.log(data, "data")
      }
    })
  }

  ngOnInit() {
    this.LoadData();
  }
  
  constructor(private http: HttpClient, private dialog: MatDialog) {
  }

  apiurl = 'http://localhost:3000/requests';


  GetAll() {
    return this.http.get(this.apiurl)
  }

  acceptUser(clientName: any) {
    let header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + localStorage.getItem('token'),
    });
    let headersObj = {
      headers: header
    };
    this.http.get(environment.BASE_API_URL + '/admin/requests/' + clientName + '/accepted', headersObj).subscribe(
      (response) => {
        console.log('User saved successfully:', response);
        this.LoadData();
      }
    );
  }

  deleteUserRequset(clientName: any) {
    let header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + localStorage.getItem('token'),
    });
    let headersObj = {
      headers: header
    };
    this.http.get(environment.BASE_API_URL + '/admin/requests/' + clientName + '/rejected', headersObj).subscribe(
      (response) => {
        console.log('User rejected successfully:', response);
        this.LoadData();
      }
    );
  }


  openDialog(user:any) {
    this.dialog.open(UserRequestDialogComponent, {
      width:'80%',
      height:'80%',
      data: user,
    });
    console.log(user)
  }
}
