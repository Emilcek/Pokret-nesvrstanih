import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';

@Component({
  selector: 'app-action-log',
  templateUrl: './action-log.component.html',
  styleUrls: ['./action-log.component.css']
})
export class ActionLogComponent implements AfterViewInit{
  displayedColumns: string[] = ['title', 'description', 'status', 'stationLead'];
  dataSource = new MatTableDataSource(table);

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }
}


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
