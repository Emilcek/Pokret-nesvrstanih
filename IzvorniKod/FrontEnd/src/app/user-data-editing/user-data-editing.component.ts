import {Component, Input, AfterViewInit,Renderer2,ElementRef,OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import { Observable } from 'rxjs';
import {FormControl, FormGroup, NgModel, Validators} from '@angular/forms';
import {Explorer} from "../explorer-profile/explorer-profile.component";
import {environment} from "../../environments/environment";

export interface Client {
  Name: string;
  Surname: string;
  Username: string;
  Password: string;
  Email: string;
  Role: string;
  ClientPhoto: any;
  Status: string;
}

@Component({
  selector: 'app-user-data-editing',
  templateUrl: './user-data-editing.component.html',
  styleUrls: ['./user-data-editing.component.css']
})



export class UserDataEditingComponent implements AfterViewInit, OnInit{
  @Input() currentUser!: Client;
  keys: any;
  role: any;

  // "Name":"Ime","Surname":"Prezime","Username":"Korisničko ime","Password":"Lozinka",
  constructor(private http: HttpClient, private route: ActivatedRoute,private renderer: Renderer2, private el: ElementRef, private router: Router) {
    localStorage["Name"]="Ime";
    localStorage["Surname"]="Prezime";
    localStorage["Username"]="Korisničko ime";
    localStorage["Email"]="Email adresa";
    localStorage["Role"]="Uloga";
    localStorage["EducatedFor"]="Kompetencije";

  }
  profileForm=new FormGroup({
    role:new FormControl('',Validators.required),
    educatedFor:new FormControl(),
    firstName:new FormControl('',[Validators.required,Validators.pattern("^[a-zA-ZčČĆćŽžŠšĐđ]+")]),
    lastName:new FormControl('',[Validators.required,Validators.pattern("^[a-zA-ZčČĆćŽžŠšĐđ]+")]),
    clientName:new FormControl('',[Validators.pattern("^[a-zA-Z0-9]+[a-zA-Z0-9]*"),Validators.required]),
    password:new FormControl(''),
    email:new FormControl('',[Validators.email,Validators.required])
  })

  userData: any;
  oldPicture: any;
  selectedOptions:any=[]
  files: any = [];
  imageURL: any;
  turnToRightFormat:any={}



  ngOnInit() {
    this.turnToRightFormat={"Name":"firstName","Surname":"lastName","Email":"email","Username":"clientName",
      "Role":"role"}
    this.role = localStorage.getItem("user");
    this.userData = this.currentUser;
    this.keys = ["Name","Surname","Email","Username","Role"]
    console.log(this.currentUser, "userdata")
    this.abilities = this.abilities.map(d=>{
      if(this.userData.EducatedFor.includes(d.name)){
        d.select=true
        return d
      }
      return d
    })
    this.profileForm.get('role')!
      .setValue(this.userData["Role"]==="tragac" ? "tragač" : (this.userData["Role"]==="voditeljPostaje" ? "voditelj postaje" : "istraživač"))
    this.profileForm.get('firstName')!.setValue(this.userData["Name"])
    this.profileForm.get('lastName')!.setValue(this.userData["Surname"])
    this.profileForm.get('clientName')!.setValue(this.userData["Username"])
    this.profileForm.get('email')!.setValue(this.userData["Email"])
    this.profileForm.get('password')!.setValue(this.userData["Password"])
    this.profileForm.get('educatedFor')!.setValue(this.userData["EducatedFor"])

    this.profileForm.get('role')!.disable()
    this.profileForm.get('clientName')!.disable()
    this.profileForm.get('email')!.disable()

    const base64String=this.userData["ClientPhoto"]; // Your Base64 image data

    const binaryImageData = atob(base64String);

    const arrayBuffer = new ArrayBuffer(binaryImageData.length);
    const uint8Array = new Uint8Array(arrayBuffer);

    for (let i = 0; i < binaryImageData.length; i++) {
      uint8Array[i] = binaryImageData.charCodeAt(i);
    }

    const blob = new Blob([uint8Array], { type: "image/jpeg" }); // Adjust the MIME type accordingly
    this.oldPicture=blob
    this.imageURL = URL.createObjectURL(blob);
  }

  ngAfterViewInit(): void {
    const imgElement= this.el.nativeElement.querySelector('#imagePreview');
    try {
      imgElement!.innerHTML=`<img src="${this.imageURL}" alt="Image Preview" height="270rem" width="auto">`
    }catch (e) {
    }
    }

  checkEducatedFor() {
    return !this.abilities.some(d => d.select);
  }
  saveUserData() {
    console.log(this.files[0])
    let header = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem('token'),
    });
    let headersObj = {
      headers: header
    };
    this.selectedOptions=[]
    if (this.profileForm.value.role==="tragac") {
      this.profileForm.get('educatedFor')!.setValidators([Validators.required]);
    } else {
      this.profileForm.get('educatedFor')!.setValidators([Validators.nullValidator]);
    }
    this.profileForm.get('educatedFor')!.updateValueAndValidity();

    if(!this.profileForm.valid){
      this.profileForm.markAllAsTouched()
      console.log("nije valid")
    }else{
      for ( let a of this.abilities ){
        if(a.select){
          this.selectedOptions.push(a.name)
        }
      }
      if(this.userData["Role"]!="tragac"){
        this.selectedOptions=[]
      }
      let formData = new FormData()
      formData.append('clientName',this.userData['Username'])
      formData.append('firstName',this.profileForm.value.firstName!)
      formData.append('lastName',this.profileForm.value.lastName!)
      formData.append('role',this.userData['Role'])
      formData.append('clientPhoto',this.files[0]==undefined ? new File([this.oldPicture],'files',{type:"image/jpeg"}): this.files[0])
      formData.append('educatedFor',this.selectedOptions)
      formData.forEach((value, key) => {
        console.log(key, value);
      });
      this.http.put(environment.BASE_API_URL+"/client",formData,headersObj).subscribe({
        next: data => {
          let response: any = data;
          console.log("poslano")
          //document.getElementById("newDataSaved")!.style.display = "block";
        }, error: (error) => {
        }
      })

    }
    this.ngOnInit();
  }
  handleFileInput(event: any): void {
    this.files=[]
    const fileInput = event.target;
    const imagePreview = document.getElementById('imagePreview');

    // Check if a file is selected
    if (fileInput.files && fileInput.files[0]) {
      const reader = new FileReader();

      reader.onload = function (e) {
        // Set the preview image source
        imagePreview!.innerHTML = `<img src="${e.target!.result}" alt="Image Preview" height="270rem" width="auto">`;
      };

      // Read the selected file as a data URL
      reader.readAsDataURL(fileInput.files[0]);
      this.files.push(fileInput.files[0]);
    } else if(this.files[0]==undefined){
      // Clear the preview if no file is selected
      imagePreview!.innerHTML = '';
    }
  }
  Checked(id: any) {
    return this.userData.EducatedFor.includes(id);
  }
  onChangeAbility(event: any){
    const id = event.target.value
    this.abilities = this.abilities.map(d=>{
      if(d.id == id){
        d.select=event.target.checked
        return d
      }
      return d
    })
  }
  refreshPage() {
    window.location.reload();
  }

  refreshData() {
    this.ngOnInit();
  }
  abilities=[
    {id:"hodanje",select:false,name:'hodanje'},
    {id:"dron",select:false,name:'dron'},
    {id:"auto",select:false,name:'auto'},
    {id:"brod",select:false,name:'brod'},
    {id:"helikopter",select:false,name:'helikopter'},
    {id:'motor',select:false,name:'motor'}
  ]
  deleteUser() {
    let header = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem('token'),
    });
    let headersObj = {
      headers: header
    };

    localStorage.removeItem("token");
    localStorage.removeItem("user");
    this.http.delete(environment.BASE_API_URL+"/client", headersObj).subscribe(
      (data) => {
        console.log("deleted")
      }
    )
    this.refreshPage();
  }
  protected readonly localStorage = localStorage;
}
