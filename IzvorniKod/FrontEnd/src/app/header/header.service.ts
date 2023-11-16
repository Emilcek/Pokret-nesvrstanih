import {Injectable, Input} from "@angular/core";
import {Subject} from "rxjs";
import {Router} from "@angular/router";

@Injectable({
    providedIn: 'root'
})

export class HeaderService {
    active = new Subject<string>();
    currentURL = window.location.href;
    page = this.currentURL.substring(this.currentURL.lastIndexOf('/') + 1);
    logged: Subject<boolean> = new Subject<boolean>();
    user: boolean = localStorage.getItem("token") !== null;

    constructor(private router: Router) {
    }

    changeActivePage(activePage: string) {
        console.log(activePage, "uspjeh");
        this.page = activePage;
        this.active.next(activePage);
    }

    userLoggedIn() {
        this.user = !this.user;
        this.logged.next(this.user);
    }

    logout() {
        localStorage.removeItem("token");
        this.router.navigate(['/login'])
    }
}
