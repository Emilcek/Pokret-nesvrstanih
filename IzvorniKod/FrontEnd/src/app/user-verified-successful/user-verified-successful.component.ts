import { Component } from '@angular/core';
import { Router } from '@angular/router';
@Component({
  selector: 'app-user-verified-successful',
  templateUrl: './user-verified-successful.component.html',
  styleUrls: ['./user-verified-successful.component.css']
})
export class UserVerifiedSuccessfulComponent {
  constructor(private router: Router) { }
  goToLogin(){
    this.router.navigateByUrl("/login")
  }
}
