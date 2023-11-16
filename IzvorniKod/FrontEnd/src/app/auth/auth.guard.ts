import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
    providedIn: 'root'
})
export class AuthGuard  {
    constructor(private auth : AuthService, private router : Router) {}

    async canActivate() {
        if(await this.auth.isUserLoggedIn()) {
            return true;
        }

        this.router.navigate(["/login"]);
        return false;
    }
}
