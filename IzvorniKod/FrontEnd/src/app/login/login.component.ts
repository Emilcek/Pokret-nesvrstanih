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
  constructor(private http:HttpClient, private router:Router){}
  signInForm = new FormGroup({
    username:new FormControl(''),
    password:new FormControl('')
  })

  signIn(){ 
    if(this.signInForm.valid){
      this.http.get("http://localhost:3000/users").subscribe((res)=>{
      let response:any=res
      const user=response.find((a:any)=>{
        return a.username===this.signInForm.value.username && a.password===this.signInForm.value.password
      });
      if(user){
        alert("Login Success")
        this.signInForm.reset()
        console.log(response.get("Username"))
      }else{
        alert("User not found")
      }
      })
    }else{
      alert("Upisite sve podatke")
    }
    
  }
}
