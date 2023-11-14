import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { UserDataEditingComponent } from './user-data-editing/user-data-editing.component';
import { ExplorerTasksComponent } from './explorer-tasks/explorer-tasks.component';
import { ExplorerProfileComponent } from './explorer-profile/explorer-profile.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register' , component: RegisterComponent },
  { path: 'edit/:id', component: UserDataEditingComponent },
  { path: 'explorer-tasks', component: ExplorerTasksComponent },
  { path: 'explorer-profile', component: ExplorerProfileComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
