import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Component, ElementRef, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import {environment} from "../../environments/environment";

@Component({
  selector: 'app-user-request-dialog',
  templateUrl: './user-request-dialog.component.html',
  styleUrls: ['./user-request-dialog.component.css']
})
export class UserRequestDialogComponent {
  userlist: any
  dataSource: any
  imageURL: any;

  constructor(@Inject(MAT_DIALOG_DATA) public datas: any, private http: HttpClient, private el: ElementRef) {}

  acceptUser(clientName: any) {
    let header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + localStorage.getItem('token'),
    });
    let headersObj = {
      headers: header
    };
    this.http.get(environment.BASE_API_URL + '/admin/requests/' + clientName + '/accepted', headersObj).subscribe(
      (response) => {
        console.log('User saved successfully:', response);
      }
    );
  }

  deleteUserRequset(clientName: any) {
    let header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + localStorage.getItem('token'),
    });
    let headersObj = {
      headers: header
    };
    this.http.get(environment.BASE_API_URL + '/admin/requests/' + clientName + '/rejected', headersObj).subscribe(
      (response) => {
        console.log('User rejected successfully:', response);
      }
    );
  }

  ngOnInit() {
    console.log('User data in dialog:', this.datas);

    //prikaz slike
    const base64String=this.datas.clientPhoto; // Your Base64 image data

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
    const imgElement= this.el.nativeElement.querySelector('.left-column');
    try {
      imgElement!.innerHTML=`<img src="${this.imageURL}" alt="Image Preview" style="height: 100%; width: 100%; object-fit: contain;">`
    }catch (e) {
    }
    }
}
