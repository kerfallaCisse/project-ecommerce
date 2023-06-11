import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FormInformations } from '../shared/models/form';
import { Router, ActivatedRoute } from '@angular/router';
import { ConfirmationService } from '../services/confirmation/confirmation.service';




@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})

export class FormComponent {



  form: FormGroup;
  country = ["Belgium", "France", "Switzerland"];
  showError: boolean = false;


  constructor(private fb: FormBuilder, private router: Router, private route: ActivatedRoute, private confirmationService: ConfirmationService) {
    this.form = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      address: ['', Validators.required],
      zipCode: ['', Validators.required],
      town: ['', Validators.required],
      country: ['Switzerland', Validators.required],
      email: ['', Validators.required],
      phoneNumber: ['', Validators.required],
    });
  }

  onSubmit() {
    if (this.form.valid) {
      const formData: FormInformations = {
        firstName: this.form.get('firstName')!.value,
        lastName: this.form.get('lastName')!.value,
        address: this.form.get('address')!.value,
        zipCode: this.form.get('zipCode')!.value,
        town: this.form.get('town')!.value,
        country: this.form.get('country')!.value,
        email: this.form.get('email')!.value,
        phoneNumber: this.form.get('phoneNumber')!.value,
      };
      this.showError = false;
      this.router.navigate(['/confirmation'], { queryParams: formData });
      this.confirmationService.setFormData(formData);

    } else {
      this.showError = true;
    }
  }

}
