import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormGroup, FormControl, Validators } from '@angular/forms'
import { Router } from '@angular/router';
import { environment } from 'src/environment/environment';
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
          this.http.get(environment.BASE_API_URL + "/client").subscribe({
            next: data => {
              console.log(data)
              localStorage.setItem("token", response.access_token);
              localStorage.setItem("user", "admin")
              this.headerService.changeActivePage("/explorer-tasks");
              this.router.navigate(['/explorer-tasks'])
              this.headerService.userLoggedIn()
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
