import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { UserDataEditingComponent } from './user-data-editing/user-data-editing.component';
import { StationLeaderProfileComponent } from './station-leader-profile/station-leader-profile.component';
import { ExplorerTasksComponent } from './explorer-tasks/explorer-tasks.component';
import { ExplorerProfileComponent } from './explorer-profile/explorer-profile.component';
import { UsersRequestsListComponent } from './users-requests-list/users-requests-list.component';
import { UsersListComponent } from './users-list/users-list.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register' , component: RegisterComponent },
  { path: 'edit/:id', component: UserDataEditingComponent },
  { path: 'stationLeader', component: StationLeaderProfileComponent},
  { path: 'explorer-tasks', component: ExplorerTasksComponent },
  { path: 'explorer-profile', component: ExplorerProfileComponent },
  { path: 'requests', component: UsersRequestsListComponent },
  { path: 'userslist', component: UsersListComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
