import {AfterViewInit, OnInit, Component, Inject, ViewChild} from '@angular/core';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {
  MatDialog,
} from '@angular/material/dialog';
import {ActionDetailsComponent} from "../action-details/action-details.component";
import {environment} from "../../environments/environment";
import {Router} from "@angular/router";
import {HeaderService} from "../header/header.service";


@Component({
  selector: 'app-action-log',
  templateUrl: './action-log.component.html',
  styleUrls: ['./action-log.component.css']
})

export class ActionLogComponent implements AfterViewInit, OnInit{

  data:any=[];
  actionId:any;
  constructor(private http:HttpClient, private router:Router, private headerService: HeaderService,public dialog: MatDialog){}
  displayedColumns: string[] = ['title', 'description', 'status', 'stationLead','moreInfo'];
  dataSource = new MatTableDataSource<any>;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngOnInit() {
    this.getActions();
    console.log(this.dataSource)
  }
  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  moreAboutAction(actionDetails:any){
    const dialog= this.dialog.open( ActionDetailsComponent, {
      width: '100%',
      data: actionDetails, //predaja podataka u dialog
      height: '30rem',

    });
  }

  getActions(){
    let header = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem('token'),
      'Content-Type': 'application/json'
    });
    let headersObj = {
      headers: header
    };
    this.http.get(environment.BASE_API_URL+"/researcher/actions",headersObj).subscribe({
      next: data => {
        this.data=data
        console.log(this.data,"this data")
        let res: any = data;
        this.dataSource = new MatTableDataSource(res);
        this.dataSource.paginator = this.paginator;
        console.log(data, "data")
      }, error: (error) => {
        console.log(error,"error")
      }
    })
    }

  }

