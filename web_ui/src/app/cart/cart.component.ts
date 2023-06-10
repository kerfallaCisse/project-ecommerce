import { Component , OnInit} from '@angular/core';
import {CartService} from '../services/cart/cart.service';
import { Item } from '../services/stock/stock.service';
import { Item2, Item3} from '../shared/models/cart';
//import {HeaderComponent} from 'src/app/header/header.component'
import { BehaviorSubject } from 'rxjs';




@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})

export class CartComponent implements OnInit{

  full_cart_client: Item2[] = [];
  full_cart_client_modifier: Item3[] = [];
  price_final: number = 0
  quantity: number[] = []
  nbr_total_bag_in_cart = 0
  cartQuantity: BehaviorSubject<number> = new BehaviorSubject<number>(0);

  
  

  constructor(private cartService: CartService) {}

  getLogoDisplayName(logo: number): string {
    if (logo === 0) {
      return 'without';
    } else if (logo === 1) {
      return 'with';
    }
    return logo.toString()
  }



  removeBag(url:String){
    this.cartService.deleteBagFromCart(url)
  }
  
  getCartQuantity() {
    return this.cartQuantity.asObservable();
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
    this.quantity.push(quantity)
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

  afficher(){
    alert("removed !")
  }
 
  ngOnInit(): void {
    
    this.cartService.get_panier_john().subscribe(data2 => { 
      
    this.full_cart_client = data2 // recupère le panier
   
    // modifie le panier avec ce qui doit être afficher
    this.full_cart_client_modifier = this.compute_price_article()  
     
     
    var subtotalElement = document.getElementById("subtotal");
    
    subtotalElement!.textContent = String(this.price_final);
    
    
    
    var subtotalElement = document.getElementById("number_article");
    

    for(let i=0;i<this.quantity.length;i++){
      this.nbr_total_bag_in_cart += this.quantity[i]
    }

 
    subtotalElement!.textContent = String(this.nbr_total_bag_in_cart);

  
  
    })

      
 
  

  
 

  }
}
