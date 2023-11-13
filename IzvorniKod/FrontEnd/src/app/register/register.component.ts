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
    console.log(this.signUpForm.valid)
    console.log(this.signUpForm.value)
    if(this.signUpForm.valid){
      if(this.signUpForm.value.role=="tragac" && this.signUpForm.value.educatedFor?.length==0){
        alert("Nisu uneseni svi podaci ili su pogrešno uneseni")
      }else{
        this.http.get("http://localhost:3000/users").subscribe((res)=>{
        let response:any=res
        const user=response.find((a:any)=>{
          console.log(a)
          return a.clientname===this.signUpForm.value.clientname && a.email===this.signUpForm.value.email
        });
        if(user==false){
          alert("Korisničko ime ili email se već koriste.")
        }else{
          this.http.post("http://localhost:3000/users",this.signUpForm.value)
          this.signUpForm.reset()
        }
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
