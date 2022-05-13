package user.com.ecommerce.service.abstraction;

import user.com.ecommerce.dto.Purchase;
import user.com.ecommerce.model.response.PurchaseResponse;

public interface ICheckout {
  PurchaseResponse placeOrder(Purchase purchase);
}
