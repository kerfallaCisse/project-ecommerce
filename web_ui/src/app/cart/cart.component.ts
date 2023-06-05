import { Component , OnInit} from '@angular/core';
import {CartService} from '../services/cart/cart.service';
import { Item } from '../services/stock/stock.service';
import { Item2, Item3} from '../shared/models/cart';




@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})

export class CartComponent implements OnInit{

  full_cart_client: Item2[] = [];
  full_cart_client_modifier: Item3[] = [];
  price_final: number = 0

  
  

  constructor(private cartService: CartService) {}

  getLogoDisplayName(logo: number): string {
    if (logo === 0) {
      return 'without';
    } else if (logo === 1) {
      return 'with';
    }
    return logo.toString()
  }

  compute_price_article(){

  
    let sum:number = 0
  
    const tableauItem3: Item3[] = this.full_cart_client.map((item2) => {
    const quantity = item2.quantity;
    let modelType = item2.modelType 
    const logo = item2.logo
    const image = item2.image
    const pocketColor = item2.pocketColor
    const bagColor = item2.bagColor

    let price: number;

    if (item2.modelType == "smallModel") {
       modelType = "40L"
       price = quantity * 130;
       sum += price
    
    } else {

      modelType = "60L"
      price = quantity * 150;
      sum += price

    }

  return {modelType,bagColor,pocketColor,logo,image, quantity, price };

});




  

  this.price_final = sum

  return tableauItem3
  }


 
  ngOnInit(): void {
    
    this.cartService.get_panier_john().subscribe(data2 => { 
      
      
    this.full_cart_client = data2
   
    
     
    this.full_cart_client_modifier = this.compute_price_article()
     
      
    var subtotalElement = document.getElementById("subtotal");
    subtotalElement!.textContent = "Nouvelle valeur";
    subtotalElement!.textContent = String(this.price_final);
    let taille_cart_client = this.full_cart_client.length
    console.log(taille_cart_client)

    var subtotalElement = document.getElementById("number_article");
    subtotalElement!.textContent = "Nouvelle valeur";
    subtotalElement!.textContent = String(taille_cart_client);

    })

      
 
  

  
 

  }
}
