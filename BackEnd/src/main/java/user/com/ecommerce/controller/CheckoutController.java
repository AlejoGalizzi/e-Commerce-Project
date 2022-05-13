package user.com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import user.com.ecommerce.dto.Purchase;
import user.com.ecommerce.model.response.PurchaseResponse;
import user.com.ecommerce.service.CheckoutService;
import user.com.ecommerce.service.abstraction.ICheckout;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

  @Autowired
  private ICheckout checkout;

  @PostMapping("/purchase")
  public PurchaseResponse placeOrder(@RequestBody Purchase purchase){
    return checkout.placeOrder(purchase);
  }
}
