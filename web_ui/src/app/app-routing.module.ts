import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { CustomizationComponent } from './customization/customization.component';
import { StockComponent } from './stock/stock.component';
import { StatisticComponent } from './statistic/statistic.component';
import { CartComponent } from './cart/cart.component';
import { AboutComponent } from './about/about.component';
import { SuccessComponent } from './success/success.component';
import { FailComponent } from './fail/fail.component';
import { FormComponent } from './form/form.component';
import { ConfirmationComponent } from './confirmation/confirmation.component';
import { HelpPageComponent } from './help-page/help-page.component';



const routes: Routes = [
{path:'',component:HomeComponent},
{path:'customization', component: CustomizationComponent},
{path:'stock', component: StockComponent},
{path:'statistic', component: StatisticComponent},
{path:'cart', component: CartComponent},
{path:'about', component: AboutComponent},
{path:'success', component: SuccessComponent},
{path:'fail', component: FailComponent},
{path: 'form', component: FormComponent},
{path: 'confirmation', component: ConfirmationComponent},
{path: 'help-page', component: HelpPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
