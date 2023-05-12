import { Injectable } from '@angular/core';
import { My3DScene } from 'src/app/customization/customization.component';

@Injectable({
  providedIn: 'root'
})


export class CustomizationService {

  private modelInstance: My3DScene | null = null;
  public isVisible: boolean = false;

  createModel(): void {
    if (!this.modelInstance) {
      this.modelInstance = new My3DScene();
      this.modelInstance.render();
    }
  }

  removeModel(): void {
    if (this.modelInstance) {
      // this.modelInstance.cleanScene();
      this.modelInstance = null;
    }
  }

  getModelInstance(): My3DScene | null {
    return this.modelInstance;
  }
}

