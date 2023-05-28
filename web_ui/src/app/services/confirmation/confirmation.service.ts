import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { FormInformations } from 'src/app/shared/models/form';

@Injectable({
  providedIn: 'root',
})
export class ConfirmationService {
  private formDataSubject = new BehaviorSubject<FormInformations | null>(null);
  formData$ = this.formDataSubject.asObservable();

  setFormData(data: FormInformations) {
    this.formDataSubject.next(data);
  }
}

