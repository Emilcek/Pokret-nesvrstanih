import { Component } from '@angular/core';

@Component({
  selector: 'app-action-creation',
  templateUrl: './action-creation.component.html',
  styleUrls: ['./action-creation.component.css']
})
export class ActionCreationComponent {
tasks :any = []

  showOptions(){
  return true;
  }
}
