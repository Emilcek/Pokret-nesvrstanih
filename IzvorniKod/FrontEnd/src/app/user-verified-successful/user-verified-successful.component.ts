import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute  } from '@angular/router';
import { environment } from 'src/environments/environment';
import {HttpClient} from '@angular/common/http';
import {HeaderService} from "../header/header.service";
@Component({
  selector: 'app-user-verified-successful',
  templateUrl: './user-verified-successful.component.html',
  standalone: true,
  styleUrls: ['./user-verified-successful.component.css']
})
export class UserVerifiedSuccessfulComponent implements OnInit{
  constructor(private router: Router, private route: ActivatedRoute, private http: HttpClient, private headerService: HeaderService) { }
  goToLogin(){
    this.headerService.changeActivePage("/login");
    this.router.navigateByUrl("/login")
  }

  ngOnInit(){
    const token = this.route.snapshot.queryParamMap.get("url")
    let route = environment.BASE_API_URL+"/auth/verified"
    console.log(route)
    this.http.post(route, token).subscribe()
  }
}
