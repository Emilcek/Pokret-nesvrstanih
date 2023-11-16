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
      this.http.post(environment.BASE_API_URL+"/auth/login", this.signInForm.value).subscribe({
        next: data => {
          let response: any = data;
          console.log(response)
          localStorage.clear()
          localStorage.setItem("token", response.access_token);
          this.headerService.userLoggedIn();
          this.router.navigate(['/explorer-tasks']);
          this.headerService.changeActivePage("/explorer-tasks");

        }, error: error => {
          alert("Korisnik nije pronađen");
        }
      })
    } else {
       alert("Upisite sve podatke");
    }
  }
}
