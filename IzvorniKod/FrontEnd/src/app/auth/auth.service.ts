import {Injectable} from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    constructor() { }

    ngOnInit() {
    }

    async isUserLoggedIn() {
        if(localStorage.getItem('token')) {
            return true;
        } else {
            return false;
        }
    }
}
