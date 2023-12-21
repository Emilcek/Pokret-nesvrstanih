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
    role:new FormControl('',Validators.required),
    educatedFor:new FormControl(),
    clientPhotoURL:new FormControl(''),
    firstName:new FormControl('',[Validators.required,Validators.pattern("^[a-zA-ZčČĆćŽžŠšĐđ]+")]),
    lastName:new FormControl('',[Validators.required,Validators.pattern("^[a-zA-ZčČĆćŽžŠšĐđ]+")]),
    clientName:new FormControl('',[Validators.pattern("^[a-zA-Z0-9]+[a-zA-Z0-9]*"),Validators.required]),
    password:new FormControl('',[Validators.minLength(8),Validators.required]),
    email:new FormControl('',[Validators.email,Validators.required]),
  })

    signUp(){
      if (this.signUpForm.value.role==="tragac") {
        this.signUpForm.get('educatedFor')!.setValidators([Validators.required]);
      } else {
        this.signUpForm.get('educatedFor')!.setValidators([Validators.nullValidator]);
      }
      this.signUpForm.get('educatedFor')!.updateValueAndValidity();

    if(!this.signUpForm.valid){
      this.signUpForm.markAllAsTouched()
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
            this.headerService.changeActivePage('/emailSent');
            this.router.navigate(['/emailSent']);
            document.getElementById("UsedUserName")!.style.display="none"
            document.getElementById("UsedEmail")!.style.display="none"
          }, error: (error) => {
            if(error.error==="Invalid client name"){
              document.getElementById("UsedUserName")!.style.display="block"
            }else if(error.error==="Invalid email"){
              document.getElementById("UsedEmail")!.style.display="block"
            }
          }
        })

    }

  }
  handleFileInput(event: any): void {
    const fileInput = event.target;
    const imagePreview = document.getElementById('imagepreview');

    // Check if a file is selected
    if (fileInput.files && fileInput.files[0]) {
      const reader = new FileReader();

      reader.onload = function (e) {
        // Set the preview image source
        imagePreview!.innerHTML = `<img src="${e.target!.result}" alt="Image Preview" height="150px" width="auto">`;
      };

      // Read the selected file as a data URL
      reader.readAsDataURL(fileInput.files[0]);
    } else {
      // Clear the preview if no file is selected
      imagePreview!.innerHTML = '';
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
