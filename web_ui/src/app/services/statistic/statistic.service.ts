import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { StatData } from '../../shared/models/statistic'
import { forkJoin } from 'rxjs';


@Injectable({
  providedIn: 'root'
})

export class StatisticService {

  constructor(private http: HttpClient) { }


  getEndpointURLs(file: string) {

    const usersEndpointURL = `api/statistics/users/${file}`;
    const ordersEndpointURL = `api/statistics/orders/${file}`;
    const colorsEndpointURL = `api/statistics/colors/${file}`;
    // const basketEndpointURL = `api/statistics/abandonned_basket/${file}`;
    // const profitEndpointURL = `api/statistics/profit/${file}`;


    return forkJoin({
      users: this.http.get(usersEndpointURL),
      orders: this.http.get(ordersEndpointURL),
      colors: this.http.get(colorsEndpointURL),
      // basket: this.http.get(basketEndpointURL),
      // profit: this.http.get(profitEndpointURL),
    });
  }

}
