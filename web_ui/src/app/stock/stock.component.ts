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
tab_real: {modelType: string,id: number,quantity: number,color_pocket_name: string,color_bag_name: string}[] = [];

color: string[] = ["black","withe","yellow","blue","red","green","gray"]
modeltype: string[] = ["SmallModel","LargeModel"]
first = this.color[0]

selectedColor_pocket: string = "nothing";
selectedColor_bag: string = "nothing";
model_bag: string = "nothing"

selectedColor_pocket_2: string = "nothing";
selectedColor_bag_2: string = "nothing";
model_bag_2: string = "nothing"

numberr: number = 0

ngOnInit(): void {

 
  this.stockService.getConfig().subscribe(data => {
    
      this.tab = data
      this.func_resume_stock()
      //console.log("tableau pas trié", this.tab)
      //console.log("tableau trié", this.tab.sort())
    })

    this.stockService.getConfig_real().subscribe(data2 => {
      
      this.tab_real = data2
      
      console.log(this.tab_real)
      this.func_resume_stock()
      
    })

    this.stockService.getConfig_2().subscribe(data2 => {
      
      console.log(data2)
    })


    

  }
    func_resume_stock(){

      let compteur_low = 0
      let compteur_out = 0

      for (var i = 0; i < this.tab.length;i++){

        if (this.tab[i].quantity == 0){
         compteur_out += 1
          }
        else if (this.tab[i].quantity < 5){
          compteur_low += 1
        }
      }
      console.log(compteur_low,compteur_out)
      const low_message  = document.getElementById("lowmessage");
      const out_message = document.getElementById("outmessage");

      if (out_message != null && low_message != null && (compteur_low != 0 || compteur_out != 0)){

          low_message.innerHTML = "> " + compteur_low + " bags with low stock";
          out_message.innerHTML = "> " +compteur_out + " bags almost out of stock";


      }
    }

    func_for_modifiquantit(color_bag: string,color_pocket: string,quantiti: number,model: string ,add: boolean) {
      
      for (var i = 0; i < this.tab.length; i++) {
        console.log(color_bag,color_pocket,model)
        console.log(this.tab[i])
        
        if (this.tab[i].color_bag_name ===  color_bag && this.tab[i].color_pocket_name === color_pocket && this.tab[i].modelType === model) {
          
          if(add == true){
          this.tab[i].quantity = this.tab[i].quantity + +quantiti;

          }
          else{
            this.tab[i].quantity = this.tab[i].quantity - +quantiti;
          }
          break
        }
      }
    }

    onInputChange(event: any) {
      this.numberr = event.target.value;
    }

    onSubmit_add() {
      this.func_for_modifiquantit(this.selectedColor_bag,this.selectedColor_pocket,this.numberr,this.model_bag,true)
      this.func_resume_stock()
    }

    onSubmit_remove() {
      this.func_for_modifiquantit(this.selectedColor_bag_2,this.selectedColor_pocket_2,this.numberr,this.model_bag_2,false)
      this.func_resume_stock()
    }

  constructor(private stockService: StockService) {}


  }














