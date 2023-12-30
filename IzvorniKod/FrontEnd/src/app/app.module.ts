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
import {NgOptimizedImage} from "@angular/common";
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
    UsersListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    NgSelectModule,
    HttpClientModule,
    FormsModule,
    UserVerifiedSuccessfulComponent,
    NgOptimizedImage,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
