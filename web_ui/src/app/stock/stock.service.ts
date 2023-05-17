import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';


import { catchError, map, retry, reduce } from 'rxjs/operators';


export interface StockItem {
  modelType: string;
  id: number;
  quantity: number;
  color_pocket_name: string;
  color_bag_name: string;
}

export type StockResponse = StockItem[];



@Injectable() 
export class StockService {

  

  configUrl = '/assets/config.json';
  configUrl_2 = '/api/stock'
  configUrl_3 = '/api/customization'



  

  constructor(private http: HttpClient) { }

  
  quantities: number[] = [];

 
  getConfig() {
    return this.http.get<StockItem[]>(this.configUrl)
  }

  getConfig_2() {
    return this.http.get(this.configUrl_3)
  }
  
  modifyQuantity(data: StockItem[]) {
      this.http.put(this.configUrl, data)
  }




  getOnelement(id: number) {
    console.log("trying to recup one element")
    return this.http.get<StockItem[]>('assets/config.json').pipe(
      map(items => items.find(item => item.id === id))
    );
      
  }
 

  


  getConfig_real() {
    console.log("trying to recup request")
    return this.http.get<StockItem[]>(this.configUrl_2)
      
  }

  



  

}
