import {Component, Input, AfterViewInit,Renderer2,ElementRef,OnInit} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import {FormControl, FormGroup, NgModel, Validators} from '@angular/forms';
import {Explorer} from "../explorer-profile/explorer-profile.component";


@Component({
  selector: 'app-user-data-editing',
  templateUrl: './user-data-editing.component.html',
  styleUrls: ['./user-data-editing.component.css']
})
export class UserDataEditingComponent implements AfterViewInit,OnInit{
  @Input() currentUser!: Explorer;
  keys: any;
  role: any;

  // "Name":"Ime","Surname":"Prezime","Username":"Korisničko ime","Password":"Lozinka",
  constructor(private http: HttpClient, private route: ActivatedRoute,private renderer: Renderer2, private el: ElementRef) {
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
    lastName:new FormControl('',[Validators.required,Validators.pattern("^[a-zA-ZčČĆćŽžŠšĐđ]+")])
  })
  apiurl = 'http://localhost:3000/user';

  userData: any;
  userId: any;
  emailError: boolean = false;
  nameError: boolean = false;

  imageInput: any;
  files: any = [];
  imageURL: any;
  ablilities: any = [];

  ngOnInit() {
    this.role = localStorage.getItem("user");
    this.userData = this.currentUser;
    this.keys = ["Name","Surname","Email","Username","Role"]
    console.log(this.currentUser, "userdata")


    const base64String=this.userData["ClientPhoto"]; // Your Base64 image data

    const binaryImageData = atob(base64String);

    const arrayBuffer = new ArrayBuffer(binaryImageData.length);
    const uint8Array = new Uint8Array(arrayBuffer);

    for (let i = 0; i < binaryImageData.length; i++) {
      uint8Array[i] = binaryImageData.charCodeAt(i);
    }

    const blob = new Blob([uint8Array], { type: "image/jpeg" }); // Adjust the MIME type accordingly
    this.imageURL = URL.createObjectURL(blob);
  }

  ngAfterViewInit(): void {

    const imgElement= this.el.nativeElement.querySelector('#imagePreview');
    imgElement!.innerHTML=`<img src="${this.imageURL}" alt="Image Preview" height="270rem" width="auto">`
    }


  saveUserData() {
    if (!this.userData.email || this.emailError || !this.userData.name || this.nameError || !this.userData.lastName || !this.userData.password) {
      // Show error message or handle the error as needed
      console.log('Invalid form input or form input is empty');
      return;
    }

    console.log('Saving user data:', this.userData);

    this.UpdateUser(this.userId, this.userData).subscribe((response) => {
      console.log('Data updated successfully', response);
      this.userData = response;
    },);
  }

  UpdateUser(id: any, userData: any): Observable<any> {
    return this.http.put(this.apiurl + '/' + id, userData);
  }

  isChecked(id: any) {
    return this.userData.EducatedFor.includes(id);
  }

  refreshPage() {
    window.location.reload();
  }
  abilities=[
    {id:"hodanje",select:false,name:'hodanje'},
    {id:"dron",select:false,name:'dron'},
    {id:"auto",select:false,name:'auto'},
    {id:"brod",select:false,name:'brod'},
    {id:"helikopter",select:false,name:'helikopter'},
    {id:'motor',select:false,name:'motor'}
  ]
  protected readonly localStorage = localStorage;
}
