import { Injectable } from '@angular/core';
import { My3DScene } from 'src/app/personalisation/personalisation.component';

@Injectable({
  providedIn: 'root'
})


export class PersonalisationService {

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
      this.modelInstance.cleanup();
      this.modelInstance = null;
    }
  }

  getModelInstance(): My3DScene | null {
    return this.modelInstance;
  }
}

