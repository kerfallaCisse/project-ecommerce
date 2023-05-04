import { NgModule } from '@angular/core';
import { NgxEchartsModule } from 'ngx-echarts';



import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { HomeComponent } from './home/home.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SearchComponent } from './search/search.component';
import { FormsModule } from '@angular/forms';
import { LoginComponent } from './login/login.component';
import { PrecisionComponent } from './login/precision/precision.component';
import { PersonalisationComponent } from './personalisation/personalisation.component';
import { StockComponent } from './stock/stock.component';
import { StatisticComponent } from './statistic/statistic.component';

// pour connecter B2D
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http'

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    SearchComponent,
    LoginComponent,
    PrecisionComponent,
    PersonalisationComponent,
    StockComponent,
    StatisticComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    HttpClientModule,
    NgxEchartsModule.forRoot({
      echarts: () => import('echarts')
    })
  ],
  providers: [],
  bootstrap: [AppComponent]

})
export class AppModule { }
