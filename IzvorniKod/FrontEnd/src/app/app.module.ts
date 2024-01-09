import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms'
import { NgSelectModule } from '@ng-select/ng-select';
import { HttpClientModule } from '@angular/common/http';
import { UserDataEditingComponent } from './user-data-editing/user-data-editing.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { StationLeaderProfileComponent } from './station-leader-profile/station-leader-profile.component';
import { ResearcherProfileComponent } from './researcher-profile/researcher-profile.component';
import { ExplorerTasksComponent } from './explorer-tasks/explorer-tasks.component';
import { ExplorerProfileComponent } from './explorer-profile/explorer-profile.component';
import { UsersRequestsListComponent } from './users-requests-list/users-requests-list.component';
import { UsersListComponent } from './users-list/users-list.component';
import { UserVerifiedSuccessfulComponent } from './user-verified-successful/user-verified-successful.component';
import { MapComponent } from './map/map.component';
import { StationInfoComponent } from './station-info/station-info.component';
import { StationChoosingComponent } from './station-choosing/station-choosing.component';
import {MatSelectModule} from '@angular/material/select';
import {NgOptimizedImage} from "@angular/common";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import { ActionCreationComponent } from './action-creation/action-creation.component';
import { EditDataAdminDialogComponent } from './edit-data-admin-dialog/edit-data-admin-dialog.component';
import {MatDialogModule} from '@angular/material/dialog';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    UserDataEditingComponent,
    LoginComponent,
    RegisterComponent,
    StationLeaderProfileComponent,
    ResearcherProfileComponent,
    ExplorerTasksComponent,
    ExplorerProfileComponent,
    UsersRequestsListComponent,
    UsersListComponent,
    ActionCreationComponent,
    UsersListComponent,
    MapComponent,
    StationInfoComponent,
    StationChoosingComponent,
    EditDataAdminDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    NgSelectModule,
    HttpClientModule,
    FormsModule,
    MatSelectModule,
    UserVerifiedSuccessfulComponent,
    NgOptimizedImage,
    BrowserAnimationsModule,
    MatTableModule,
    MatPaginatorModule,
    MatDialogModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
