import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms'
import { Router } from '@angular/router';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  constructor(private http:HttpClient, private router:Router){}

  signUpForm=new FormGroup({
    role:new FormControl(''),
    educatedFor:new FormControl(''),
    clientPhotoURL:new FormControl(''),
    firstname:new FormControl(''),
    lastname:new FormControl(''),
    clientname:new FormControl('',Validators.pattern('[a-zA-z]+$')),
    password:new FormControl('',Validators.minLength(8)),
    email:new FormControl('',Validators.email),
  })

  signUp(){
    if(this.signUpForm.valid){
      if(this.signUpForm.value.role=="tragac" && this.signUpForm.value.educatedFor?.length==0){
        alert("Nisu uneseni svi podaci ili su pogrešno uneseni")
      }else{
        this.http.post("http://localhost:8008/auth/register",this.signUpForm.value,{ observe: 'response' }).subscribe((res)=>{
        console.log(res.status)
        },error=>{
          alert("Korisničko ime ili email se već koriste.")
        })
      }
    }else{
      alert("Nisu uneseni svi podaci ili su pogrešno uneseni")
    }
  }
  showDiv(event:any){
    if(event.target.value=="tragac"){
      document.getElementById("abilities")!.style.display="block"
    }else{
      document.getElementById("abilities")!.style.display="none"
    }
  }

  abilities=[
    {id:1,name:'hodanje'},
    {id:2,name:'dron'},
    {id:3,name:'auto'},
    {id:4,name:'brod'},
    {id:5,name:'cross motor'},
    {id:6,name:'helikopter'}
  ]

}
