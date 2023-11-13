import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormComponent } from './form/form.component';
import { UserDataEditingComponent } from './user-data-editing/user-data-editing.component';

const routes: Routes = [
  {path: "", component: FormComponent},
  { path: 'edit/:id', component: UserDataEditingComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
