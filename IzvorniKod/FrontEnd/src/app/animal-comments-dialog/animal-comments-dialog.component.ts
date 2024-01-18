import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Component, ElementRef, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import {environment} from "../../environments/environment";
import { CommaExpr } from '@angular/compiler';

interface postData {
  commentTimestamp: string;
  animalComment: string;
  explorerName: string;
}

@Component({
  selector: 'app-animal-comments-dialog',
  templateUrl: './animal-comments-dialog.component.html',
  styleUrls: ['./animal-comments-dialog.component.css']
})
export class AnimalCommentsDialogComponent implements OnInit{
  comments: any[] | undefined;
  animalId: any;
  newComment: string = '';
  role: any;

  header = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + localStorage.getItem('token'),
  });
  headersObj = {
    headers: this.header
  };

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private http: HttpClient, private el: ElementRef) {}

  ngOnInit() {

    this.http.get<any>(environment.BASE_API_URL + "/client", this.headersObj).subscribe({
      next: data => {
        console.log("This is data:", data)
        let res: any = data;
        this.role = data.role
        console.log("role: " + this.role)
      }
    })

    console.log("Comments: ", this.data.dataForDialog);
    console.log("AnimalId: ", this.data.animalId);

    this.comments = this.data.dataForDialog.map((comment: { animalComment: any; }) => comment.animalComment);
    console.log("Commestn2: " + this.comments)
    this.animalId = this.data.animalId;
  }

  addComment() {
    if(this.newComment.trim() !== '') {

      const commentData: postData = {
        commentTimestamp: "2024-01-17 13:38:57", 
        animalComment: this.newComment,
        explorerName: this.data.nameOfExplorer
    };
    console.log("Data for post: " + JSON.stringify(commentData) )
      this.http.post(environment.BASE_API_URL + "/animalcomment/create/" + this.data.animalId, commentData, this.headersObj).subscribe({
        next: responseData => {
          console.log("Dodan komentar: " + responseData);
        },
        error: error => {
          console.error("Error sending location:", error);
          console.log("Error Status: " + error.status);
          console.log("Error Message: " + error.message);
        }
      })
      console.log("New comment: " + this.newComment)
      this.newComment = '';
    }
  }
}


