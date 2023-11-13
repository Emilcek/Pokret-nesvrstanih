import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormGroup, FormControl, Validators } from '@angular/forms'
import { Router } from '@angular/router';
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
      this.http.post("http://localhost:8080/auth/login", this.signInForm.value, { observe: 'response' }).subscribe((response: any) => {
        console.log(response.status)
      }, error => {
        alert("User not found")
      }) 
    }else{
       alert("Upisite sve podatke")
    }
  }
}
