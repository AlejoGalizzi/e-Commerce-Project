import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OrderHistory } from '../common/order-history';

@Injectable({
  providedIn: 'root'
})
export class OrderHistoryService {

  orderHistoryUrl: string = "http://localhost:8080/api/orders";
  
  constructor(private httpClient: HttpClient) { }

  getOrderHistory(email: string): Observable<GetResponseOrderHistory> {
    const url = `${this.orderHistoryUrl}/search/findByCustomerEmail?email=${email}`;

    return this.httpClient.get<GetResponseOrderHistory>(url);
  }
}

interface GetResponseOrderHistory {
  _embedded: {
    orders: OrderHistory[];
  }
}