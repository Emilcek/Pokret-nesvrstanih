import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import {FormGroup,FormControl,Validators} from '@angular/forms'
import { Router } from '@angular/router';
@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent {
  constructor(private http:HttpClient, private router:Router){}
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
    username:new FormControl(''),
    password:new FormControl(''),
    email:new FormControl(''),
  })
  signIn(){
    this.http.post<any>("http://localhost:3000/comments",this.signInForm.value)
    .subscribe((res)=>{
      const user=res.find((a:any)=>{
        return a.username===this.signInForm.value.username && a.password===this.signInForm.value.password
      });
      if(user){
        alert("Login Success")
        this.signInForm.reset()
        //this.router.navigate([''])
      }else{
        alert("user not found")
      }
      this.signUpForm.reset()
    })
    
  }

  signUp(){
    this.http.post<any>("http://localhost:3000/comments",this.signUpForm.value)
    .subscribe(res=> {
      const user=res.find((a:any)=>{
        return a.username!=this.signUpForm.value.username && a.email!=this.signUpForm.value.email
      })
      if(user){
      alert("Success")
      this.signUpForm.reset()
      }else{
        alert("Username or email already in use")
        this.signUpForm.reset()
      }
    })
  }
  showDiv(event:any){
    if(event.target.value=="tragac"){
      document.getElementById("stations")!.style.display="none"
      document.getElementById("abilities")!.style.display="block"
    } else if(event.target.value=="voditeljPostaje"){
      document.getElementById("stations")!.style.display="block"
      document.getElementById("abilities")!.style.display="none"
    }else{
      document.getElementById("abilities")!.style.display="none"
      document.getElementById("stations")!.style.display="none"
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
