import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-users-requests-list',
  templateUrl: './users-requests-list.component.html',
  styleUrls: ['./users-requests-list.component.css']
})
export class UsersRequestsListComponent {

  constructor(private http: HttpClient) {
    this.LoadUser();
  }

  apiurl = 'http://localhost:3000/requests';

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

  acceptUser(user: any, index: number) {
    this.http.post('http://localhost:3000/user', user).subscribe(
      (response) => {
        console.log('User saved successfully:', response);
        this.deleteUserRequset(user.id, index);
      }
    );
  }

  deleteUserRequset(userId: string, index: number) {
    this.dataSource.splice(index, 1);
    const deleteUserUrl = `${this.apiurl}/${userId}`;
    this.http.delete(deleteUserUrl).subscribe(
      (response) => {
        console.log('User deleted from server successfully:', response);
      }
    );
  }
}
