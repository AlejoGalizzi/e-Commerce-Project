import { Injectable } from '@angular/core';
import { BehaviorSubject, ReplaySubject, Subject } from 'rxjs';
import { CartItem } from '../common/cart-item';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  cartItems: CartItem[] = [];
  totalPrice: Subject<number> = new BehaviorSubject<number>(0);
  totalQuantity: Subject<number> = new BehaviorSubject<number>(0);
  storage: Storage = localStorage;

  constructor() { 
    let data = JSON.parse(this.storage.getItem('cartItems'));
    if(data != null) {
      this.cartItems = data;

      this.computeCartTotals();
    }
  }

  addToCart(cartItem: CartItem){
    let alreadyExistInCart:boolean = false;
    let existingCartItem: CartItem = undefined!;

    existingCartItem = this.cartItems.find(item => item.id === cartItem.id)!;
    
    alreadyExistInCart = (existingCartItem != undefined);
    if(alreadyExistInCart){
      existingCartItem.quantity++;
    }else{
      this.cartItems.push(cartItem);
    }
    this.computeCartTotals();
  }
  
  persistCartItems(){
    this.storage.setItem('cartItems',JSON.stringify(this.cartItems));
  }

  decrementQuanqtity(cartItem: CartItem){
    cartItem.quantity--;
    if(cartItem.quantity == 0){
      this.remove(cartItem);
    }else this.computeCartTotals();
  }

  remove(cartItem: CartItem) {
    if(this.cartItems.indexOf(cartItem)> -1){
      this.cartItems.splice(this.cartItems.indexOf(cartItem),1);
    }
    this.computeCartTotals();
  }

  computeCartTotals() {
    let totalPriceValue: number = 0;
    let totalQuantityValue: number = 0;
    for (let item of this.cartItems) {
      totalPriceValue += item.unitPrice*item.quantity;
      totalQuantityValue += item.quantity;
    }

    this.totalPrice.next(totalPriceValue);
    this.totalQuantity.next(totalQuantityValue);
    this.persistCartItems();
  }
}
