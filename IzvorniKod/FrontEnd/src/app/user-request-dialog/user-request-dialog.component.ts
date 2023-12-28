import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import {environment} from "../../environments/environment";

@Component({
  selector: 'app-user-request-dialog',
  templateUrl: './user-request-dialog.component.html',
  styleUrls: ['./user-request-dialog.component.css']
})
export class UserRequestDialogComponent {
  userlist: any
  dataSource: any

  constructor(@Inject(MAT_DIALOG_DATA) public datas: any, private http: HttpClient) {}

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
        console.log(res, "Data u load: data")
      }
    })
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

  ngOnInit() {
    console.log('User data in dialog:', this.datas);
  }

}
