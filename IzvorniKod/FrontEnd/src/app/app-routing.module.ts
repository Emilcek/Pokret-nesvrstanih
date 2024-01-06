import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { StationLeaderProfileComponent } from './station-leader-profile/station-leader-profile.component';
import { ResearcherProfileComponent } from './researcher-profile/researcher-profile.component';
import { ExplorerTasksComponent } from './explorer-tasks/explorer-tasks.component';
import { ExplorerProfileComponent } from './explorer-profile/explorer-profile.component';
import { UsersRequestsListComponent } from './users-requests-list/users-requests-list.component';
import { UsersListComponent } from './users-list/users-list.component';
import { UserVerifiedSuccessfulComponent } from './user-verified-successful/user-verified-successful.component';
import {AuthGuard} from "./auth/auth.guard";
import {AuthLoginGuard} from "./auth/auth.loginguard";
import {StationChoosingComponent} from "./station-choosing/station-choosing.component";
import {StationInfoComponent} from "./station-info/station-info.component";
import { AnimalLocationComponent } from './animal-location/animal-location.component';
import { AnimalLocationMockComponent } from './animal-location-mock/animal-location-mock.component';
const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent, canActivate:[AuthLoginGuard]},
  { path: 'register' , component: RegisterComponent, canActivate:[AuthLoginGuard]},
  { path: 'explorer-tasks', component: ExplorerTasksComponent, canActivate:[AuthGuard], data: { requiredRole: 'tragac' }},
  { path: 'explorer-profile', component: ExplorerProfileComponent, canActivate:[AuthGuard], data: { requiredRole: 'tragac' } },
  { path: 'users-list', component: UsersListComponent, canActivate:[AuthGuard], data: { requiredRole: 'admin' }},
  { path: 'users-request-list', component: UsersRequestsListComponent, canActivate:[AuthGuard], data: { requiredRole: 'admin' }},
  { path: 'verified' , component: UserVerifiedSuccessfulComponent},
  { path: 'explorer-profile', component: ExplorerProfileComponent, canActivate:[AuthGuard], data: { requiredRole: 'tragac' } },
  { path: 'choose-station', component: StationChoosingComponent, canActivate:[AuthGuard], data: { requiredRole: 'voditeljPostaje' } },
  { path: 'station-info', component: StationInfoComponent, canActivate:[AuthGuard], data: { requiredRole: 'voditeljPostaje' } },
  { path: 'researcher-profile', component: ResearcherProfileComponent, canActivate:[AuthGuard], data: { requiredRole: 'istrazivac' } },
  { path: 'station-leader-profile', component: StationLeaderProfileComponent, canActivate:[AuthGuard], data: { requiredRole: 'voditeljPostaje' } },
  { path: 'animal' , component: AnimalLocationComponent},
  { path: 'animal-mock' , component: AnimalLocationMockComponent},
  { path: '**', component: LoginComponent, canActivate:[AuthLoginGuard]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]

})
export class AppRoutingModule { }
