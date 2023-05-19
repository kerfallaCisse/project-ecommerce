import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component'
import { PrecisionComponent } from './login/precision/precision.component'
import { CustomizationComponent } from './customization/customization.component';
import { StockComponent } from './stock/stock.component';
import { StatisticComponent } from './statistic/statistic.component';
import { CartComponent } from './cart/cart.component';
import { AboutComponent } from './about/about.component';



const routes: Routes = [
{path:'',component:HomeComponent},
{path: 'login', component: LoginComponent },
{path: 'precision', component: PrecisionComponent},
{path: 'login', component: LoginComponent, children: [
  {path: 'precision', component: PrecisionComponent }
]},
{path:'customization', component: CustomizationComponent},
{path:'stock', component: StockComponent},
{path:'statistic', component: StatisticComponent},
{path:'cart', component: CartComponent},
{path:'about', component: AboutComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
