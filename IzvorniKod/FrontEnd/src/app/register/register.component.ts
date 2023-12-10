import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms'
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { HeaderService } from "../header/header.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  constructor(private http:HttpClient, private router:Router, private headerService: HeaderService){}
  selectedOptions:string[]=[]
  signUpForm=new FormGroup({
    role:new FormControl('',),
    educatedFor:new FormControl(),
    clientPhotoURL:new FormControl('',Validators.required),
    firstName:new FormControl('',Validators.required),
    lastName:new FormControl('',Validators.required),
    clientName:new FormControl('',[Validators.pattern("^[a-zA-Z]+[a-zA-Z0-9]*"),Validators.required]),
    password:new FormControl('',[Validators.minLength(8),Validators.required]),
    email:new FormControl('',[Validators.email,Validators.required]),
  })
    signUp(){
    if(this.signUpForm.valid){
      if(this.signUpForm.value.role==="tragac" && this.signUpForm.value.educatedFor?.length===0){
        alert("Nisu uneseni svi podaci ili su pogrešno uneseni")
      }else{
        for ( let a of this.abilities ){
          if(a.select){
            this.selectedOptions.push(a.name)
          }
        }
        if(this.signUpForm.value.role!="tragac"){
          this.selectedOptions=[]
        }
        this.signUpForm.value.educatedFor=this.selectedOptions
        this.http.post(environment.BASE_API_URL+"/auth/register",this.signUpForm.value).subscribe({
          next: data => {
            let response: any = data;
            alert("Poslan vam je verifikacijski mail na vašu email adresu");
            this.headerService.changeActivePage('/login');
            this.router.navigate(['/login']);
          }, error: error => {
            alert("Korisničko ime ili email se već koriste.")
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

  onChangeAbility(event: any){
    const id = event.target.value
    const isChecked = event.target.checked
    this.abilities = this.abilities.map(d=>{
      if(d.id == id){
        d.select=isChecked
        return d
      }
      return d
    })
  }

  abilities=[
    {id:"hodanje",select:false,name:'hodanje'},
    {id:"dron",select:false,name:'dron'},
    {id:"auto",select:false,name:'auto'},
    {id:"brod",select:false,name:'brod'},
    {id:'cross motor',select:false,name:'cross motor'},
    {id:"helikopter",select:false,name:'helikopter'}
  ]

}
