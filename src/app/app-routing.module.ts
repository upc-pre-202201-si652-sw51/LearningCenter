import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './public/home/home.component';
import { AboutComponent } from './public/about/about.component';
import { StudentsComponent } from './students/pages/students/students.component';
import { SignInComponent } from './security/pages/sign-in/sign-in.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: '', redirectTo: ' home', pathMatch: 'full' },
  { path: 'students', component: StudentsComponent },
  { path: 'sign-in', component: SignInComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
