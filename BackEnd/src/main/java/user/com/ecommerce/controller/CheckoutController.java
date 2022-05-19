package user.com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import user.com.ecommerce.dto.Purchase;
import user.com.ecommerce.model.response.PurchaseResponse;
import user.com.ecommerce.service.abstraction.ICheckout;


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
