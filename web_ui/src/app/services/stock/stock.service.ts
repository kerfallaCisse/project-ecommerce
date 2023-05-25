import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

import {StockItem} from '../../shared/models/stock'


import { catchError, map, retry, reduce } from 'rxjs/operators';


export interface Item {
  modelType: string;
  color_pocket_name: string;
  color_bag_name: string;
  quantity: number;
}



export type StockResponse = StockItem[];



@Injectable()
export class StockService {

  

  
  configUrl_2 = '/api/stock'
  configUrl_3 = '/api/customization'
  configUrl_4 = '/api/stock/update'



  constructor(private http: HttpClient) { }


  quantities: number[] = [];

  getConfig_real() {
    console.log("trying to recup request")
    return this.http.get<StockItem[]>(this.configUrl_2)
  }

  modifiy_donne(model: string,color_bag_name:string,color_pocket_name: string,quantity: number){
    var y: number = +quantity;
    const data: Item = {
      modelType:  model,
      color_pocket_name: color_pocket_name,
      color_bag_name: color_bag_name,
      
      quantity: -y,
      
    };
    console.log(data)
    this.http.post<Item>(this.configUrl_2,data).subscribe(response => {
      console.log(response)
    })
    
    ;
}


  




}
