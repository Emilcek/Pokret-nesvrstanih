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
    clientName: new FormControl('',[Validators.pattern("^[a-zA-Z0-9]+[a-zA-Z0-9]*"),Validators.required]),
    password: new FormControl('',[Validators.minLength(8),Validators.required])
  })

  signIn() {
    document.getElementById("notVerified")!.style.display = "none";
    document.getElementById("doesntExist")!.style.display = "none";
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
              this.headerService.userLoggedIn(true)
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
          if(error.error==="Client not verified"){
          document.getElementById("notVerified")!.style.display = "block";
          }else{
            document.getElementById("doesntExist")!.style.display = "block";
          }
        }
      })

  }

}
