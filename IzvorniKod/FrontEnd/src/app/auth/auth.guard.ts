import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
    providedIn: 'root'
})
export class AuthGuard  {

    constructor(private auth : AuthService, private router : Router) {}

  canActivate(
  next: ActivatedRouteSnapshot,
) {
      const requiredRole = next.data['requiredRole'];
      console.log(requiredRole);
      if(!this.auth.isUserLoggedIn()) {
          this.router.navigate(["/login"]);
          return false;
        }

      const userRole = this.auth.getUserRole();

      if (userRole === requiredRole) {
        // User has the required role, allow access
        return true;
      } else {
        // User does not have the required role, redirect to login or another page
        this.router.navigate(['/login']);
        return false;
      }
    }
}
