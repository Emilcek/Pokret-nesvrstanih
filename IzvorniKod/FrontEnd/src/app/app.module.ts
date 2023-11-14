import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import{ FormsModule, ReactiveFormsModule }from '@angular/forms'
import { NgSelectModule } from '@ng-select/ng-select';
import { HttpClientModule } from '@angular/common/http';
import { UserDataEditingComponent } from './user-data-editing/user-data-editing.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { UsersRequestsListComponent } from './users-requests-list/users-requests-list.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    UserDataEditingComponent,
    LoginComponent,
    RegisterComponent,
    UsersRequestsListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    NgSelectModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
