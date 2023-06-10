import { NgModule } from '@angular/core';
import { NgxEchartsModule } from 'ngx-echarts';



import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { HomeComponent } from './home/home.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { LoginComponent } from './login/login.component';
import { PrecisionComponent } from './login/precision/precision.component';
import { CustomizationComponent } from './customization/customization.component';
import { StockComponent } from './stock/stock.component';
import { StatisticComponent } from './statistic/statistic.component';

// pour connecter B2D
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import { CartComponent } from './cart/cart.component';
import { AboutComponent } from './about/about.component';
import { FormComponent } from './form/form.component';
import { SuccessComponent } from './success/success.component';
import { FailComponent } from './fail/fail.component'
import { ReactiveFormsModule } from '@angular/forms';
import { ConfirmationComponent } from './confirmation/confirmation.component';
import { AuthModule } from '@auth0/auth0-angular';
import { environment } from './environnement';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    LoginComponent,
    PrecisionComponent,
    CustomizationComponent,
    StockComponent,
    StatisticComponent,
    CartComponent,
    AboutComponent,
    FormComponent,
    SuccessComponent,
    FailComponent,
    ConfirmationComponent,
    
  ],
  imports: [
    AuthModule.forRoot({
      // ancien 
      domain: 'dev-xuzmuq3g0kbtxrc4.us.auth0.com',
      clientId: 'ulU0Nnga1ilqIpGjcjXLkHSvNkplcYW6',
      // domain: 'dev-xuzmuq3g0kbtxrc4.us.auth0.com',
      // clientId: '9WMd9HGMRo6Hkx700leDW5wmPMa4U1Gg',
      authorizationParams: {
        redirect_uri: window.location.origin
      }
    }),
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgxEchartsModule.forRoot({
      echarts: () => import('echarts')
    })
  ],
  providers: [CartComponent],
  bootstrap: [AppComponent]

})
export class AppModule { }
