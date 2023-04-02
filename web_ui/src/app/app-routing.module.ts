import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import{SacPageComponent} from './sac-page/sac-page.component';
import { LoginComponent } from './login/login.component'
import { PrecisionComponent } from './login/precision/precision.component' 



const routes: Routes = [
{path:'',component:HomeComponent},
{path:'search/:searchTerm',component:HomeComponent},
{path:'tag/:tag',component:HomeComponent},
{path: 'food/:id',component:SacPageComponent},
{ path: 'login', component: LoginComponent },
{path: 'precision', component: PrecisionComponent},
{ path: 'login', component: LoginComponent, children: [
  { path: 'precision', component: PrecisionComponent }
]},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
