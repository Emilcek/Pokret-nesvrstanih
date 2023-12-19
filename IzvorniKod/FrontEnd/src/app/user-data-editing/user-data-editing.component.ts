import { Component, OnInit, Input } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { NgModel } from '@angular/forms';
import {Explorer} from "../explorer-profile/explorer-profile.component";


@Component({
  selector: 'app-user-data-editing',
  templateUrl: './user-data-editing.component.html',
  styleUrls: ['./user-data-editing.component.css']
})
export class UserDataEditingComponent implements OnInit {
  @Input() currentUser!: Explorer;
  keys: any;
  role: any;

  // "Name":"Ime","Surname":"Prezime","Username":"Korisničko ime","Password":"Lozinka",
  constructor(private http: HttpClient, private route: ActivatedRoute) {
    localStorage["Name"]="Ime";
    localStorage["Surname"]="Prezime";
    localStorage["Username"]="Korisničko ime";
    localStorage["Password"]="Lozinka";
    localStorage["Email"]="Email adresa";
    localStorage["Role"]="Uloga";
  }

  apiurl = 'http://localhost:3000/user';

  userData: any;
  userId: any;
  emailError: boolean = false;
  nameError: boolean = false;

  ngOnInit(): void {
    /*this.route.paramMap.subscribe((params) => {
      this.userId = params.get('id');
      this.getUserData();
    });*/
    this.role = localStorage.getItem("user");
    this.userData = this.currentUser;
    this.keys = Object.keys(this.userData)
    console.log(this.currentUser, "userdata")
  }


  getUserData() {
    this.GetUserbyCode(this.userId).subscribe((data) => {
      this.userData = data;
    });
  }

  saveUserData() {
    if (!this.userData.email || this.emailError || !this.userData.name || this.nameError || !this.userData.surname || !this.userData.lozinka) {
      // Show error message or handle the error as needed
      console.log('Invalid form input or form input is empty');
      return;
    }

    console.log('Saving user data:', this.userData);

    this.UpdateUser(this.userId, this.userData).subscribe((response) => {
      console.log('Data updated successfully', response);
      this.userData = response;
    },);
  }

  GetUserbyCode(id: any): Observable<any> {
    return this.http.get(this.apiurl + '/' + id);
  }

  UpdateUser(id: any, userData: any): Observable<any> {
    return this.http.put(this.apiurl + '/' + id, userData);
  }

  validateEmail() {
    const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    if (!emailPattern.test(this.userData.email)) {
      this.emailError = true;
    } else {
      this.emailError = false;
    }
  }

  protected readonly localStorage = localStorage;
}
