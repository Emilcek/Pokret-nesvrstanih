import {Component, OnInit, ViewChild} from '@angular/core';
import { Input } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Router } from '@angular/router';
import {environment} from "../../environments/environment";
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent implements OnInit {
  userlist: any
  //dataSource: any

  displayedColumns: string[] = ['clientName', 'firstName', 'lastName', 'email', 'role', 'Update'];
  dataSource = new MatTableDataSource<any>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

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
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.paginator = this.paginator;
        console.log(data, "data")
      }
    })
  }

  apiurl = environment.BASE_API_URL + '/explorer';

  LoadUser() {
    this.GetAll().subscribe(res => {
      //this.dataSource = res;
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
