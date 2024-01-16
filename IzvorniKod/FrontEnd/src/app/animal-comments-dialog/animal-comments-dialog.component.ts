import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Component, ElementRef, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import {environment} from "../../environments/environment";

@Component({
  selector: 'app-animal-comments-dialog',
  templateUrl: './animal-comments-dialog.component.html',
  styleUrls: ['./animal-comments-dialog.component.css']
})
export class AnimalCommentsDialogComponent {
  comments: any[] = [
    "Desiii",
    "Sa ilaa",
    "kao gos gogrs grk0d grs g rdo grdo grdo grop esio fseno grio gres graen gsirep"
  ];
  newComment: string = '';

  header = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + localStorage.getItem('token'),
  });
  headersObj = {
    headers: this.header
  };

  constructor(@Inject(MAT_DIALOG_DATA) public datas: any, private http: HttpClient, private el: ElementRef) {}


  //post rekvest za dodatak komentara
  addComment() {
    if(this.newComment.trim() !== '') {
      //post rekvest za dodatak komentara po animaId
      /*this.http.post(environment.BASE_API_URL + "" + this.datas.animaId, this.newComment, this.headersObj).subscribe({
        next: responseData => {
          console.log("Dodan komentar: " + responseData);
        },
        error: error => {
          console.error("Error sending location:", error);
          console.log("Error Status: " + error.status);
          console.log("Error Message: " + error.message);
        }
      })*/
      console.log("New comment: " + this.newComment)
      this.comments.push(this.newComment)
      this.newComment = '';
      console.log("Dodaj komentar: " + this.datas.animalSpecies)
    }
  }
}


