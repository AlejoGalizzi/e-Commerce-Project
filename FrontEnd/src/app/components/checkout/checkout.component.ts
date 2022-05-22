import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Event, Router } from '@angular/router';
import { Country } from 'src/app/common/country';
import { CustomValidators } from 'src/app/validators/custom-validators';
import { State } from 'src/app/common/state';
import { ShopFormService } from 'src/app/services/shop-form.service';
import { CartService } from 'src/app/services/cart.service';
import { CheckoutService } from 'src/app/services/checkout.service';
import { Order } from 'src/app/common/order';
import { OrderItem } from 'src/app/common/order-item';
import { Purchase } from 'src/app/common/purchase';
import { environment } from 'src/environments/environment';
import { PaymentInfo } from 'src/app/common/payment-info';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  stripe = Stripe(environment.stripePublishableKey);

  paymentInfo: PaymentInfo = new PaymentInfo();
  cardElement: any;
  displayError: any = "";

  checkoutFormGroup: FormGroup;

  countries: Country[] = [];
  shippingStates: State[] = [];
  billingStates: State[] = [];

  storage: Storage = sessionStorage;

  totalPrice: number = 0.00;
  totalQuantity: number = 0;

  constructor(private formBuilder: FormBuilder,
    private shopFormService: ShopFormService,
    private cartService: CartService,
    private checkoutService: CheckoutService,
    private router: Router) { }

  ngOnInit(): void {

    this.setupStripePaymentForm();

    const email = JSON.parse(this.storage.getItem('userEmail'));

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
        email: new FormControl(email,
          [Validators.required,
          Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$'),
          CustomValidators.notOnlyWhiteSpace])
      }),
      shippingAddress: this.formBuilder.group({
        country: new FormControl('', [Validators.required]),
        street: new FormControl('', [Validators.required,
          Validators.minLength(2),
          CustomValidators.notOnlyWhiteSpace]),
        city: new FormControl('', [Validators.required,
          Validators.minLength(2),
          CustomValidators.notOnlyWhiteSpace]),
        state: new FormControl('', [Validators.required]),
        zipCode: new FormControl('', [Validators.required,
          Validators.minLength(2),
          CustomValidators.notOnlyWhiteSpace])
      }),
      billingAddress: this.formBuilder.group({
        country: new FormControl('', [Validators.required]),
        street: new FormControl('', [Validators.required,
            Validators.minLength(2),
            CustomValidators.notOnlyWhiteSpace]),
        city: new FormControl('', [Validators.required,
            Validators.minLength(2),
            CustomValidators.notOnlyWhiteSpace]),
        state: new FormControl('', [Validators.required]),
        zipCode: new FormControl('', [Validators.required,
            Validators.minLength(2),
            CustomValidators.notOnlyWhiteSpace])
      })
    });

    this.shopFormService.getCountryList().subscribe(
      data => {
        this.countries = data;
      }
    )
    
    this.updateCartStatus();
  }

  setupStripePaymentForm() {

    var elements = this.stripe.elements();

    this.cardElement = elements.create('card',{hidePostalCode: true});
    this.cardElement.mount('#card-element');

    this.cardElement.on('change',(event)=>{
      this.displayError = document.getElementById('card-errors');

      if(event.complete) {
        this.displayError.textContent = "";
      }else if(event.error) {
        this.displayError.textContent = event.error.message;
      }
    });
  }

  updateCartStatus() {
    this.cartService.totalPrice.subscribe(
      data => this.totalPrice = data
    );
    this.cartService.totalQuantity.subscribe(
      data => this.totalQuantity = data
    )
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
    if(this.checkoutFormGroup.invalid){
      this.checkoutFormGroup.markAllAsTouched();
      return;
    }

    let order = new Order();
    order.totalPrice = this.totalPrice;
    order.totalQuantity = this.totalQuantity;
    const cartItems = this.cartService.cartItems;
    let orderItems: OrderItem[] = cartItems.map(tempItem => new OrderItem(tempItem));
    let purchase = new Purchase();

    purchase.customer = this.checkoutFormGroup.controls['customer'].value;

    purchase.shippingAddress = this.checkoutFormGroup.controls['shippingAddress'].value;
    const shippingState: State = JSON.parse(JSON.stringify(purchase.shippingAddress.state));
    const shippingCountry: Country = JSON.parse(JSON.stringify(purchase.shippingAddress.country));
    purchase.shippingAddress.state = shippingState.name;
    purchase.shippingAddress.country = shippingCountry.name;

    purchase.billingAddress = this.checkoutFormGroup.controls['billingAddress'].value;
    const billingState: State = JSON.parse(JSON.stringify(purchase.billingAddress.state));
    const billingCountry: Country = JSON.parse(JSON.stringify(purchase.billingAddress.country));
    purchase.billingAddress.state = billingState.name;
    purchase.billingAddress.country = billingCountry.name;

    purchase.orderItems = orderItems;
    purchase.order = order;


    this.paymentInfo.amount = Math.round(this.totalPrice * 100);
    this.paymentInfo.currency = "USD";
    
    

    if (!this.checkoutFormGroup.invalid && this.displayError.textContent === "") {
      

      this.checkoutService.createPaymentIntent(this.paymentInfo).subscribe(
        (paymentIntentResponse) => {
          this.stripe.confirmCardPayment(paymentIntentResponse.client_secret,
            {
              payment_method: {
                card: this.cardElement,
                billing_details: {
                  email: purchase.customer.email,
                  name: `${purchase.customer.firstName} ${purchase.customer.firstName}`,
                  address: {
                    line1: purchase.billingAddress.street,
                    city: purchase.billingAddress.city,
                    state: purchase.billingAddress.state,
                    postal_code: purchase.billingAddress.zipCode,
                    country: this.billingCountry.value.code
                  }
                }
              }
            }, { handleActions: false })
          .then(function(result) {
            if (result.error) {
              alert(`There was an error: ${result.error.message}`);
            } else {
              this.checkoutService.placeOrder(purchase).subscribe({
                next: response => {
                  alert(`Your order has been received.\nOrder tracking number: ${response.orderTrackingNumber}`);
                  this.resetCart();
                },
                error: err => {
                  alert(`There was an error: ${err.message}`);
                }
              })
            }            
          }.bind(this));
        }
      );
    } else {
      this.checkoutFormGroup.markAllAsTouched();
      return;
    }

  }

  resetCart() {
    this.cartService.cartItems = [];
    this.cartService.totalPrice.next(0);
    this.cartService.totalQuantity.next(0);
    this.cartService.persistCartItems();
    this.checkoutFormGroup.reset();

    this.router.navigateByUrl("/products");
  }

  copyShippingAddressToBillingAddress(event) {
    if (event.target.checked) {
      this.checkoutFormGroup.controls['billingAddress']
        .setValue(this.checkoutFormGroup.controls['shippingAddress'].value);
      this.billingStates = this.shippingStates;
    }
    else {
      this.checkoutFormGroup.controls['billingAddress'].reset();
    }
  }


  get firstName() {return this.checkoutFormGroup.get('customer.firstName');}
  get lastName() {return this.checkoutFormGroup.get('customer.lastName');}
  get email() {return this.checkoutFormGroup.get('customer.email');}
  get shippingStreet() {return this.checkoutFormGroup.get('shippingAddress.street');}
  get shippingCity() {return this.checkoutFormGroup.get('shippingAddress.city');}
  get shippingCountry() {return this.checkoutFormGroup.get('shippingAddress.country');}
  get shippingState() {return this.checkoutFormGroup.get('shippingAddress.state');}
  get shippingZipCode() {return this.checkoutFormGroup.get('shippingAddress.zipCode');}
  get billingStreet() {return this.checkoutFormGroup.get('billingAddress.street');}
  get billingCity() {return this.checkoutFormGroup.get('billingAddress.city');}
  get billingCountry() {return this.checkoutFormGroup.get('billingAddress.country');}
  get billingState() {return this.checkoutFormGroup.get('billingAddress.state');}
  get billingZipCode() {return this.checkoutFormGroup.get('billingAddress.zipCode');}
}
