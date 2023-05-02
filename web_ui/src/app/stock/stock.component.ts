import { Component, OnInit } from '@angular/core';
import { StockItem, StockResponse, StockService } from './stock.service';

@Component({
  selector: 'app-config',
  templateUrl: './stock.component.html',
  providers: [ StockService ],
  styleUrls: ['./stock.component.css'] // <-- ajoute cette ligne
})



export class StockComponent implements OnInit {


quantities: number[] = []


tab: {modelType: string,id: number,quantity: number,color_pocket_name: string,color_bag_name: string}[] = [];
color: string[] = ["black","withe","yellow","blue","red","green","gray"]
first = this.color[0]

selectedColor_pocket: string = "nothing";
selectedColor_bag: string = "nothing";

numberr: number = 0

ngOnInit(): void {
    
    
    this.stockService.getConfig().subscribe(data => {
      this.tab = data
    })

  }


    func_for_modifiquantit(color_bag: string,color_pocket: string,quantiti: number,add: boolean) {

      for (var i = 0; i < this.tab.length; i++) {
        console.log("on rentre dans la modif")
        if (this.tab[i].color_bag_name ===  color_bag && this.tab[i].color_pocket_name === color_pocket ) {
          if(add == true){
          this.tab[i].quantity = this.tab[i].quantity + +quantiti;
          }
          else{
            this.tab[i].quantity = this.tab[i].quantity - +quantiti;
          }
          console.log("tab changed")
          console.log(this.tab)
          break
        }
      }
    }

    onInputChange(event: any) {
      this.numberr = event.target.value;
    }

    onSubmit_add() {
      this.func_for_modifiquantit(this.selectedColor_bag,this.selectedColor_pocket,this.numberr,true)
    }

    onSubmit_remove() {
      this.func_for_modifiquantit(this.selectedColor_bag,this.selectedColor_pocket,this.numberr,false)
    }
     

    

  

  constructor(private stockService: StockService) {}
  

  
  
  
    
    
      
  }
    

  // console.log("showConfig")
  // this.stockService.getConfig().subscribe(data => {
  //   this.quantities = data.map(item => item.quantity);
  //   console.log(this.quantities)
  // });

      









