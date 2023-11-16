import { Component } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { FormGroup, FormControl, Validators } from '@angular/forms'
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import {HeaderService} from "../header/header.service";
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(private http: HttpClient, private router: Router, private headerService: HeaderService) {
  }

  signInForm = new FormGroup({
    clientName: new FormControl(''),
    password: new FormControl('')
  })

  signIn() {
    if (this.signInForm.valid) {
      console.log(this.signInForm.value)
      this.http.post(environment.BASE_API_URL+"/auth/login", this.signInForm.value).subscribe({
        next: data => {
          let response: any = data;
          localStorage.setItem("token", response.access_token);
          let header = new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
          });
          let headersObj = {
            headers: header
          };
          this.http.get<any>(environment.BASE_API_URL + "/client", headersObj).subscribe({
            next: data => {
              console.log(data)
              let res: any = data;
              localStorage.setItem("user", data.role);
              this.headerService.userLoggedIn()
              this.headerService.roleChanged(data.role);
              if(data.role === "tragac") {
                this.headerService.changeActivePage("/explorer-tasks");
                this.router.navigate(['/explorer-tasks'])
              } else if(data.role === "istrazivac") {
                this.headerService.changeActivePage("/researcher-profile");
                this.router.navigate(['/researcher-profile'])
              } else if(data.role === "voditeljPostaje") {
                this.headerService.changeActivePage("/station-leader-profile");
                this.router.navigate(['/station-leader-profile'])
              } else if(data.role === "admin") {
                this.headerService.changeActivePage("/users-list");
                this.router.navigate(['/users-list'])
              }
            }
          })

        }, error: error => {
          alert("Korisnik nije pronađen.")
        }
      })
    } else {
      alert("Upisite sve podatke")
    }
  }

}
