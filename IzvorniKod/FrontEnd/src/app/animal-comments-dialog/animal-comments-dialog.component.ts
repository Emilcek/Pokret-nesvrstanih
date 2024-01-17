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

  header = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + localStorage.getItem('token'),
  });
  headersObj = {
    headers: this.header
  };

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private http: HttpClient, private el: ElementRef) {}

  ngOnInit() {
    console.log("Comments: ", this.data.dataForDialog);
    console.log("AnimalId: ", this.data.animalId);

    // Assign data to component properties
    this.comments = this.data.dataForDialog.map((comment: { animalComment: any; }) => comment.animalComment);
    console.log("Commestn2: " + this.comments)
    this.animalId = this.data.animalId;
  }

  //post rekvest za dodatak komentara
  addComment() {
    if(this.newComment.trim() !== '') {

      const commentData: postData = {
        commentTimestamp: "2024-01-17 13:38:57", // You can use a library like moment.js for more formatting options
        animalComment: this.newComment,
        explorerName: this.data.nameOfExplorer // Replace with the actual explorer name
    };
    console.log("Data for post: " + JSON.stringify(commentData) )
      //post rekvest za dodatak komentara po animaId
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


