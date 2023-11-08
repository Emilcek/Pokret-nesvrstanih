import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import {FormGroup,FormControl,Validators} from '@angular/forms'
import { Router } from '@angular/router';
//import { AuthService } from '../service/auth.service';
@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent {
  constructor(private http:HttpClient, private router:Router){}
  result:any
  signInForm = new FormGroup({
    username:new FormControl(''),
    password:new FormControl('')
  })
  signUpForm=new FormGroup({
    position:new FormControl(''),
    ability:new FormControl(''),
    station:new FormControl(''),
    photo:new FormControl(''),
    name:new FormControl(''),
    surname:new FormControl(''),
    username:new FormControl('',Validators.pattern('[a-zA-z]+$')),
    password:new FormControl('',Validators.minLength(8)),
    email:new FormControl('',Validators.email),
  })
  signIn(){ //PRIJAVI SE
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

  signUp(){ //REGISTRIRAJ SE
    console.log(this.signUpForm.valid)
    if(this.signUpForm.valid){
      if(this.signUpForm.value.position=="tragac" && this.signUpForm.value.ability?.length==0){
        alert("Nisu uneseni svi podaci ili su pogrešno uneseni")
      }else{
        this.http.get("http://localhost:3000/users").subscribe((res)=>{
        let response:any=res
        const user=response.find((a:any)=>{
          console.log(a)
          return a.username===this.signUpForm.value.username && a.email===this.signUpForm.value.email
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
      // document.getElementById("stations")!.style.display="none"
      document.getElementById("abilities")!.style.display="block"
    // } else if(event.target.value=="voditeljPostaje"){
    //   document.getElementById("stations")!.style.display="block"
    //   document.getElementById("abilities")!.style.display="none"
    }else{
      document.getElementById("abilities")!.style.display="none"
      // document.getElementById("stations")!.style.display="none"
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

  stations=[
    {id:1,name:'Biokovo'},
    {id:2,name:'Lonjsko polje'}
  ]
  
}
