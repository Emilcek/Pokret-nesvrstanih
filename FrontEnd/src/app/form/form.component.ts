import { Component } from '@angular/core';
import {FormGroup,FormControl,Validators} from '@angular/forms'
@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent {
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
    if(localStorage.getItem(this.signInForm.value.username as string)!==null){
      console.log(this.signInForm.value)
    } else {
      alert("Pogrešno uneseno korisničko ime ili lozinka")
    }
    
  }

  signUp(){
    if(this.signInForm.pristine){
      alert("Ispunite sva navedena polja s podacima")
  }
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
