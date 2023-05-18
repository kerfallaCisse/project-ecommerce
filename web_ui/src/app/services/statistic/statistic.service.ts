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
    const usersEndpointURL = `/assets/stats-mocks/users/${file}.json`;
    const ordersEndpointURL = `/assets/stats-mocks/orders/${file}.json`;
    const colorsEndpointURL = `/assets/stats-mocks/colors/${file}.json`;


    return forkJoin({
      users: this.http.get(usersEndpointURL),
      orders: this.http.get(ordersEndpointURL),
      colors: this.http.get(colorsEndpointURL),
    });
  }

}
