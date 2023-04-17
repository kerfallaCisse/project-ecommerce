import { Injectable } from '@angular/core';
import {Food} from '../../shared/models/Food'
@Injectable({
  providedIn: 'root'
})
export class FoodService {

  constructor() { }


  getFoodById(id: number): Food{
    return this.getAll().find(food => food.id == id)!;
  }
  getAll():Food[]{
    return [
    {
      id:1,
      name:'Montagne',
      cooktime:'10-12',
      favorite:false,
      origins: ['USA','France'],
      price: 130,
      imageUrl: 'assets/images/sac1.jpg',
      stars: 4.7
    },
    {
      id:2,
      name:'Voyage',
      cooktime:'10-12',
      favorite:false,
      origins: ['Suisse','France'],
      price: 100,
      imageUrl: 'assets/images/sac2.jpg',
      stars: 5
    },
    {
      id:3,
      name:'Sport',
      cooktime:'10-12',
      favorite:true,
      origins: ['Suisse','France'],
      price: 100,
      imageUrl: 'assets/images/sac3.jpg',
      stars: 4.7
    },
    {
      id:4,
      name:'Travail',
      cooktime:'10-12',
      favorite:false,
      origins: ['Suisse','France'],
      price: 100,
      imageUrl: 'assets/images/sac4.jpg',
      stars: 4.7
    },
  ]}
}
