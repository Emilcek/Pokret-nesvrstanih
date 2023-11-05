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

  })
  signIn(){
    if(this.signInForm.value.username!=undefined && localStorage.getItem(this.signInForm.value.username) 
    && this.signInForm.value.password==localStorage.getItem(this.signInForm.value.username)){
      console.log("Uspješna prijava")
    }else{
      console.log("Username or password are invalid or you don't have an account")
    }
  }

  signUp(){
    
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
  
}
