import { Injectable } from '@angular/core';
import { My3DScene } from 'src/app/customization/customization.component';
import { HttpClient, HttpHeaders } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})


export class CustomizationService {

  constructor(private http: HttpClient) { }
  private modelInstance: My3DScene | null = null;
  public isVisible: boolean = false;


  getQuantityOfUrl(){
    return this.http.get<number>('/api/stock/quantity?modelType=smallModel&bagColor=black&pocketColor=black')
  }


  createModel(): void {
    if (!this.modelInstance) {
      this.modelInstance = new My3DScene();
      this.modelInstance.render();
    }
  }

  removeModel(): void {
    if (this.modelInstance) {
      
      this.modelInstance = null;
    }
  }

  getModelInstance(): My3DScene | null {
    return this.modelInstance;
  }
}

