import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Event } from '@angular/router';
import { Country } from 'src/app/common/country';
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
        firstName: [''],
        lastName: [''],
        email: ['']
      }),
      shippingAddress: this.formBuilder.group({
        country: [''],
        street: [''],
        city: [''],
        state: [''],
        zipCode: ['']
      }),
      billingAddress: this.formBuilder.group({
        country: [''],
        street: [''],
        city: [''],
        state: [''],
        zipCode: ['']
      }),
      creditCard: this.formBuilder.group({
        cardType: [''],
        cardName: [''],
        cardNumber: [''],
        securityCode: [''],
        expirationMonth: [''],
        expirationYear:['']
      })
    });

    const startWith: number = new Date().getMonth() + 1;
    console.log("Stat Month: "+ startWith);
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

  handleMonthAndYears(){
    const creditCardFormGroup = this.checkoutFormGroup.get('creditCard');

    const currentYear: number = new Date().getFullYear();
    const selectedYear: number = Number(creditCardFormGroup.value.expirationYear);

    let startMonth: number;

    if(currentYear === selectedYear){
      startMonth = new Date().getMonth() + 1;
    }else{
      startMonth = 1;
    }

    this.setMonthsArray(startMonth);
  }

  handleStates(formGroupName: string){
    const formGroup = this.checkoutFormGroup.get(formGroupName);
    const countryCode = formGroup.value.country.code;

    this.shopFormService.getStateList(countryCode).subscribe(
      data => {
        if(formGroupName == 'shippingAddress'){
          this.shippingStates = data;
        }else{
          this.billingStates = data;
        }

        formGroup.get('state').setValue(data[0]);
      }
    )
  }

  onSubmit  (){
    console.log("Handling the submit button");
    console.log(this.checkoutFormGroup.get('customer')!.value);
    console.log("The email address is"+ this.checkoutFormGroup.get('customer').value.email);
    console.log("The shipping address country is: "+this.checkoutFormGroup.get('shippingAddress').value.country.name);
    console.log("The shipping address state is: "+this.checkoutFormGroup.get('shippingAddress').value.state.name);
    
    
  }

  copyShippingAdressToBillingAddress(event){
    if(event.target.checked){
      this.checkoutFormGroup.controls['billingAddress']
          .setValue(this.checkoutFormGroup.controls['shippingAddress'].value);
      this.billingStates = this.shippingStates;
    }
    else{
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

}
