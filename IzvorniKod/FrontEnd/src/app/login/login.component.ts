import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormGroup, FormControl, Validators } from '@angular/forms'
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(private http: HttpClient, private router: Router) {
  }

  signInForm = new FormGroup({
    clientName: new FormControl(''),
    password: new FormControl('')
  })

  signIn() {
    if (this.signInForm.valid) {
      console.log(this.signInForm.value)
      this.http.post(environment.BASE_API_URL+"/auth/login", this.signInForm.value, { observe: 'response' }).subscribe((response: any) => {
        console.log(response.status)
      }, error => {
        alert("Korisnik nije pronađen")
      })
    }else{
       alert("Upisite sve podatke")
    }
  }
}
