package user.com.ecommerce.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import user.com.ecommerce.dto.PaymentInfo;
import user.com.ecommerce.dto.Purchase;
import user.com.ecommerce.model.response.PurchaseResponse;
import user.com.ecommerce.service.abstraction.ICheckout;


@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

  @Autowired
  private ICheckout checkout;

  @PostMapping("/purchase")
  public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {
    return checkout.placeOrder(purchase);
  }

  @PostMapping("/payment-intent")
  public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo)
      throws StripeException {
    PaymentIntent paymentIntent = checkout.createPaymentIntent(paymentInfo);
    String pIntent = paymentIntent.toJson();

    return new ResponseEntity<>(pIntent, HttpStatus.OK);
  }
}
