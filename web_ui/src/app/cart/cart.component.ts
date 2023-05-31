import { Component , OnInit} from '@angular/core';
import {CartService} from '../services/cart/cart.service';
import { Item } from '../services/stock/stock.service';


export interface Item2 {
  modelType: string;
  bagColor: string;
  pocketColor: string;
  logo: number;
  image: string;
  quantity: number
}

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})

export class CartComponent implements OnInit{

  tout_panier: {modelType: string,bagColor: string,pocketColor: string,logo: number,image: string,quantity: number}[] = [];
  tout_modif: {modelType: string,bagColor: string,pocketColor: string,logo: number,image: string,quantity: number}[] = [];

  
  

  constructor(private cartService: CartService) {}

  func_for_modif(){
    let updatedItems = this.tout_panier.map(item => {
      console.log("salut_2")
      if (item.modelType === "smallModel") {
        return { ...item, modelType: "40L",bagColor: item.bagColor.toUpperCase(),pocketColor: item.pocketColor.toUpperCase() };
      } else if (item.modelType === "largeModel") {
        return { ...item, modelType: "50L",  bagColor: item.bagColor.toUpperCase(),pocketColor: item.pocketColor.toUpperCase()};
      } 
      const logoStatus = item.logo === 0 ? "No" : "Yes";

      return { ...item, bagColor: item.bagColor.toUpperCase(),logo:logoStatus };

    });
    
  }

  ngOnInit(): void {
    this.cartService.get_panier_john().subscribe(data2 => { 
      
      
      this.tout_panier = data2
      console.log(this.tout_panier)
      this.func_for_modif()
    })


    
  }

}
