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


  openDialog(user:any) {
    const dialogRef = this.dialog.open(UserRequestDialogComponent, {
      width:'80%',
      height:'75%',
      data: user,
    });
    console.log(user)
    dialogRef.afterClosed().subscribe(result => {
      this.LoadData();
    });
  }
}
