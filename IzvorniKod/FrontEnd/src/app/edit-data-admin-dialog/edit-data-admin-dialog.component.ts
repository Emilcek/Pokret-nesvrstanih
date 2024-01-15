import { Component, Inject, OnInit, ViewEncapsulation } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-edit-data-admin-dialog',
  templateUrl: './edit-data-admin-dialog.component.html',
  styleUrls: ['./edit-data-admin-dialog.component.css'],
})
export class EditDataAdminDialogComponent implements OnInit{

  constructor(@Inject(MAT_DIALOG_DATA) public currentUser: any){}

  ngOnInit() {
    console.log("Current user: " + this.currentUser.clientName)
  }
}
