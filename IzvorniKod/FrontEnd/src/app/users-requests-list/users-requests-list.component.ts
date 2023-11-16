import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {environment} from "../../environment/environment";

@Component({
  selector: 'app-users-requests-list',
  templateUrl: './users-requests-list.component.html',
  styleUrls: ['./users-requests-list.component.css']
})
export class UsersRequestsListComponent implements OnInit {
  userlist: any
  dataSource: any

  ngOnInit() {
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
  constructor(private http: HttpClient) {
  }

  apiurl = 'http://localhost:3000/requests';



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
    /*this.http.post('http://localhost:3000/user', user).subscribe(
      (response) => {
        console.log('User saved successfully:', response);
        this.deleteUserRequset(user.id, index);
      }
    );*/
  }

  deleteUserRequset(userId: string, index: number) {
    /*this.dataSource.splice(index, 1);
    const deleteUserUrl = `${this.apiurl}/${userId}`;
    this.http.delete(deleteUserUrl).subscribe(
      (response) => {
        console.log('User deleted from server successfully:', response);
      }
    );*/
  }
}
