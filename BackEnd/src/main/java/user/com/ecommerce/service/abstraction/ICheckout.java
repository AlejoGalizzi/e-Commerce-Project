package user.com.ecommerce.service.abstraction;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import user.com.ecommerce.dto.PaymentInfo;
import user.com.ecommerce.dto.Purchase;
import user.com.ecommerce.model.response.PurchaseResponse;

public interface ICheckout {
  PurchaseResponse placeOrder(Purchase purchase);

  PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;
}
