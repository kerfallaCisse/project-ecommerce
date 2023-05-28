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
      first_name: ['', Validators.required],
      last_name: ['', Validators.required],
      address: ['', Validators.required],
      zip_code: ['', Validators.required],
      town: ['', Validators.required],
      country: ['Switzerland', Validators.required],
      mail: ['', Validators.required],
      phone_number: ['', Validators.required],
    });
  }

  onSubmit() {
    if (this.form.valid) {
      const formData: FormInformations = {
        first_name: this.form.get('first_name')!.value,
        last_name: this.form.get('last_name')!.value,
        address: this.form.get('address')!.value,
        zip_code: this.form.get('zip_code')!.value,
        town: this.form.get('town')!.value,
        country: this.form.get('country')!.value,
        mail: this.form.get('mail')!.value,
        phone_number: this.form.get('phone_number')!.value,
      };
      console.log(formData);
      this.showError = false;
      this.router.navigate(['/confirmation'], { queryParams: formData });
      this.confirmationService.setFormData(formData);

    } else {
      this.showError = true;
    }
  }

}
