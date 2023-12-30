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
files: any=[]
  constructor(private http:HttpClient, private router:Router, private headerService: HeaderService){}
  selectedOptions:string[]=[]
  signUpForm=new FormGroup({
    role:new FormControl('',Validators.required),
    educatedFor:new FormControl(),
    firstName:new FormControl('',[Validators.required,Validators.pattern("^[a-zA-ZčČĆćŽžŠšĐđ]+")]),
    lastName:new FormControl('',[Validators.required,Validators.pattern("^[a-zA-ZčČĆćŽžŠšĐđ]+")]),
    clientName:new FormControl('',[Validators.pattern("^[a-zA-Z0-9]+[a-zA-Z0-9]*"),Validators.required]),
    password:new FormControl('',[Validators.minLength(8),Validators.required]),
    email:new FormControl('',[Validators.email,Validators.required])
  })

  checkEducatedFor() {
    return !this.abilities.some(d => d.select) && this.signUpForm.value.role === "tragac"
      && this.signUpForm.get('educatedFor')!.touched;
  }

  checkPhoto(){
  return this.files[0]==undefined || this.files[0]==null
  }
    signUp(){
      document.getElementById("UsedUserName")!.style.display="none"
      document.getElementById("UsedEmail")!.style.display="none"
      document.getElementById("verifyLink")!.style.display = "none";
      if(this.files[0]==undefined){
        document.getElementById("noPhotoError")!.style.display= "block";
      }
      console.log(this.files[0])
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
        let formData = new FormData()
        formData.append('role',this.signUpForm.value.role!)
        formData.append('clientPhoto',this.files[0])
        formData.append('educatedFor',this.signUpForm.value.educatedFor)
        formData.append('firstName',this.signUpForm.value.firstName!)
        formData.append('lastName',this.signUpForm.value.lastName!)
        formData.append('clientName',this.signUpForm.value.clientName!)
        formData.append('password',this.signUpForm.value.password!)
        formData.append('email',this.signUpForm.value.email!)
      //formData.forEach((value, key) => {
      //  console.log(key, value);
      //});
        this.http.post(environment.BASE_API_URL+"/auth/register",formData).subscribe({
          next: data => {
            let response: any = data;
            document.getElementById("verifyLink")!.style.display = "block";
            window.scrollTo({ top: 0, behavior: 'smooth' });
            }, error: (error) => {
            console.log(error,"error")
            console.log(this.files[0])
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
      this.files.push(fileInput.files[0]);
    } else if(this.files[0]==undefined){
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
        d.select=true
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
    {id:'motor',select:false,name:'motor'},
    {id:"helikopter",select:false,name:'helikopter'}
  ]

}
