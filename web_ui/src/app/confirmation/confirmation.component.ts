import { Component } from '@angular/core';
import { ConfirmationService } from '../services/confirmation/confirmation.service';


@Component({
  selector: 'app-confirmation',
  templateUrl: './confirmation.component.html',
  styleUrls: ['./confirmation.component.css']
})
export class ConfirmationComponent {

  formData$ = this.confirmationService.formData$;

  constructor(private confirmationService: ConfirmationService) { }


}
