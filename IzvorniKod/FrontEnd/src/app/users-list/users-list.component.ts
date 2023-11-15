import { Component } from '@angular/core';
import { Input } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent {

  constructor(private http: HttpClient, private router: Router) {
    this.LoadUser();
  }

  apiurl = 'http://localhost:3000/user';

  userlist: any
  dataSource: any

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
    this.router.navigate([`admin/userdata/edit/${userId}`]);
  }

}
