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
  comments = [
    "This is a great website!",
    "I love the content here.",
    "Amazing work!",
    "Keep it up!",
    "I learned a lot from this site.",
    "Awesome job!",
    "This is very helpful.",
    "I appreciate the effort.",
    "Fantastic content!",
    "I recommend this to everyone."
  ];

  constructor(@Inject(MAT_DIALOG_DATA) public datas: any, private http: HttpClient, private el: ElementRef) {}


  //post rekvest za dodatak komentara
  addComment() {
    console.log("Dodaj komentar: " + this.datas.animalSpecies)
  }
}


