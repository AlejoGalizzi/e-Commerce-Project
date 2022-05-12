import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Event } from '@angular/router';
import { Country } from 'src/app/common/country';
import { CustomValidators } from 'src/app/validators/custom-validators';
import { State } from 'src/app/common/state';
import { ShopFormService } from 'src/app/services/shop-form.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  checkoutFormGroup: FormGroup;

  creditCardMonths: number[] = [];
  creditCardYears: number[] = [];
  countries: Country[] = [];
  shippingStates: State[] = [];
  billingStates: State[] = [];


  totalPrice: number = 0;
  totalQuantity: number = 0;

  constructor(private formBuilder: FormBuilder,
    private shopFormService: ShopFormService) { }

  ngOnInit(): void {

    this.checkoutFormGroup = this.formBuilder.group({
      customer: this.formBuilder.group({
        firstName: new FormControl('', 
          [Validators.required,
            Validators.minLength(2),
            CustomValidators.notOnlyWhiteSpace]),
        lastName: new FormControl('',
          [Validators.required,
            Validators.minLength(2),
            CustomValidators.notOnlyWhiteSpace]),
        email: new FormControl('',
          [Validators.required,
          Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2-4}$'),
          CustomValidators.notOnlyWhiteSpace])
      }),
      shippingAddress: this.formBuilder.group({
        country: [''],
        street: new FormControl('', [Validators.required, Validators.minLength(2)]),
        city: new FormControl('', [Validators.required, Validators.minLength(2)]),
        state: [''],
        zipCode: new FormControl('', [Validators.required, Validators.minLength(2)])
      }),
      billingAddress: this.formBuilder.group({
        country: [''],
        street: new FormControl('', [Validators.required, Validators.minLength(2)]),
        city: new FormControl('', [Validators.required, Validators.minLength(2)]),
        state: [''],
        zipCode: new FormControl('', [Validators.required, Validators.minLength(2)])
      }),
      creditCard: this.formBuilder.group({
        cardType: [''],
        cardName: new FormControl('', [Validators.required, Validators.minLength(2)]),
        cardNumber: new FormControl('',
          [Validators.required,
          Validators.pattern('^[0-9]*$'),
          Validators.minLength(16),
          Validators.maxLength(16)]),
        securityCode: new FormControl('',
          [Validators.required,
          Validators.pattern('^[0-9]*$'),
          Validators.minLength(3),
          Validators.maxLength(3)]),
        expirationMonth: [''],
        expirationYear: ['']
      })
    });

    const startWith: number = new Date().getMonth() + 1;
    console.log("Stat Month: " + startWith);
    this.setMonthsArray(startWith);

    this.shopFormService.getCountryList().subscribe(
      data => {
        this.countries = data;
      }
    )

    this.shopFormService.getCreditCardYears().subscribe(
      data => {
        this.creditCardYears = data;
      }
    );
  }

  handleMonthAndYears() {
    const creditCardFormGroup = this.checkoutFormGroup.get('creditCard');

    const currentYear: number = new Date().getFullYear();
    const selectedYear: number = Number(creditCardFormGroup.value.expirationYear);

    let startMonth: number;

    if (currentYear === selectedYear) {
      startMonth = new Date().getMonth() + 1;
    } else {
      startMonth = 1;
    }

    this.setMonthsArray(startMonth);
  }

  handleStates(formGroupName: string) {
    const formGroup = this.checkoutFormGroup.get(formGroupName);
    const countryCode = formGroup.value.country.code;

    this.shopFormService.getStateList(countryCode).subscribe(
      data => {
        if (formGroupName == 'shippingAddress') {
          this.shippingStates = data;
        } else {
          this.billingStates = data;
        }

        formGroup.get('state').setValue(data[0]);
      }
    )
  }

  onSubmit() {
    console.log("Handling the submit button");
    console.log(this.checkoutFormGroup.get('customer')!.value);
    console.log("The email address is" + this.checkoutFormGroup.get('customer').value.email);
    console.log("The shipping address country is: " + this.checkoutFormGroup.get('shippingAddress').value.country.name);
    console.log("The shipping address state is: " + this.checkoutFormGroup.get('shippingAddress').value.state.name);

    if(this.checkoutFormGroup.invalid){
      this.checkoutFormGroup.markAllAsTouched();
    }

    console.log("CheckoutFormGroup is valid: "+ this.checkoutFormGroup.valid);
    
  }

  copyShippingAdressToBillingAddress(event) {
    if (event.target.checked) {
      this.checkoutFormGroup.controls['billingAddress']
        .setValue(this.checkoutFormGroup.controls['shippingAddress'].value);
      this.billingStates = this.shippingStates;
    }
    else {
      this.checkoutFormGroup.controls['billingAddress'].reset();
    }
  }

  setMonthsArray(startMonth: number) {
    this.shopFormService.getCreditCardMonths(startMonth).subscribe(
      data => {
        console.log("Retrieve card months: " + JSON.stringify(data));
        this.creditCardMonths = data;
      }
    );
  }

  get firstName() {
    return this.checkoutFormGroup.get('customer.firstName');
  }
  get lastName() {
    return this.checkoutFormGroup.get('customer.lastName');
  }
  get email() {
    return this.checkoutFormGroup.get('customer.email');
  }

}
