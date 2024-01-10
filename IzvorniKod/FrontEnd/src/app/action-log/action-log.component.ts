import {AfterViewInit, Component, Inject, ViewChild} from '@angular/core';
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

export class ActionLogComponent implements AfterViewInit{
  constructor(private http:HttpClient, private router:Router, private headerService: HeaderService,public dialog: MatDialog){}
  displayedColumns: string[] = ['title', 'description', 'status', 'stationLead','moreInfo'];
  dataSource = new MatTableDataSource(table);
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  moreAboutAction(){
    const dialog= this.dialog.open( ActionDetailsComponent, {
      width: '100%',
      data: {name: 'test'}, //predaja podataka u dialog
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
    this.http.get(environment.BASE_API_URL + "/researcher/actions", headersObj).subscribe((res: any) => {
      console.log(res);
    });
    }

  }



//napraviti get request na backend za sve akcije i onda ih prikazati u tablici
const table = [
  {actionName: 'Rjesavanje problema sa svinjama', actionDescription: 'Pokusavamo istražiti zašto svinje umiru na zadanim područjima', actionStatus: 1.0079, stationLead: 'Branko Jurić'},
  {actionName: 'Rjesavanje problema sa svinjama', actionDescription: 'Pokusavamo istražiti zašto svinje umiru na zadanim područjima', actionStatus: 1.0079, stationLead: 'H'},
  {actionName: 'Rjesavanje problema sa svinjama', actionDescription: 'Pokusavamo istražiti zašto svinje umiru na zadanim područjima', actionStatus: 1.0079, stationLead: 'H'},
  {actionName: 'Rjesavanje problema sa svinjama', actionDescription: 'Pokusavamo istražiti zašto svinje umiru na zadanim područjima', actionStatus: 1.0079, stationLead: 'H'},
  {actionName: 'Rjesavanje problema sa svinjama', actionDescription: 'Pokusavamo istražiti zašto svinje umiru na zadanim područjima', actionStatus: 1.0079, stationLead: 'H'},
  {actionName: 'Rjesavanje problema sa svinjama', actionDescription: 'Pokusavamo istražiti zašto svinje umiru na zadanim područjima', actionStatus: 1.0079, stationLead: 'H'},
  {actionName: 'Rjesavanje problema sa svinjama', actionDescription: 'Pokusavamo istražiti zašto svinje umiru na zadanim područjima', actionStatus: 1.0079, stationLead: 'H'},
  {actionName: 'Rjesavanje problema sa svinjama', actionDescription: 'Pokusavamo istražiti zašto svinje umiru na zadanim područjima', actionStatus: 1.0079, stationLead: 'H'},
  {actionName: 'Rjesavanje problema sa svinjama', actionDescription: 'Pokusavamo istražiti zašto svinje umiru na zadanim područjima', actionStatus: 1.0079, stationLead: 'H'},
  {actionName: 'Rjesavanje problema sa svinjama', actionDescription: 'Pokusavamo istražiti zašto svinje umiru na zadanim područjima', actionStatus: 1.0079, stationLead: 'H'},
  {actionName: 'Rjesavanje problema sa svinjama', actionDescription: 'Pokusavamo istražiti zašto svinje umiru na zadanim područjima', actionStatus: 1.0079, stationLead: 'H'},
  {actionName: 'Rjesavanje problema sa svinjama', actionDescription: 'Pokusavamo istražiti zašto svinje umiru na zadanim područjima', actionStatus: 1.0079, stationLead: 'H'},
  {actionName: 'Rjesavanje problema sa svinjama', actionDescription: 'Pokusavamo istražiti zašto svinje umiru na zadanim područjima', actionStatus: 1.0079, stationLead: 'H'},

];
