package user.com.ecommerce.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.transaction.Transactional;
import org.hibernate.engine.internal.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import user.com.ecommerce.dto.PaymentInfo;
import user.com.ecommerce.dto.Purchase;
import user.com.ecommerce.model.entity.Customer;
import user.com.ecommerce.model.entity.Order;
import user.com.ecommerce.model.entity.OrderItem;
import user.com.ecommerce.model.response.PurchaseResponse;
import user.com.ecommerce.repository.ICustomerRepository;
import user.com.ecommerce.service.abstraction.ICheckout;

@Service
public class CheckoutService implements ICheckout {

  @Autowired
  private ICustomerRepository customerRepository;

  public CheckoutService(@Value("${stripe.key.secret}") String secretKey){
    Stripe.apiKey = secretKey;
  }

  @Override
  @Transactional
  public PurchaseResponse placeOrder(Purchase purchase) {

    Order order = purchase.getOrder();

    String orderTrackingNumber = generateOrderTrackingNumber();
    order.setOrderTrackingNumber(orderTrackingNumber);

    Set<OrderItem> orderItems = purchase.getOrderItems();
    orderItems.forEach(item -> order.add(item));

    order.setShippingAddress(purchase.getShippingAddress());
    order.setBillingAddress(purchase.getBillingAddress());

    Customer customer = purchase.getCustomer();
    Customer customerFromDB = customerRepository.findByEmail(customer.getEmail());
    if(customerFromDB != null){
      customer = customerFromDB;
    }

    customer.add(order);
    customerRepository.save(customer);

    return new PurchaseResponse(orderTrackingNumber);
  }

  @Override
  public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {

    List<String> paymentMethodTypes = new ArrayList<>();
    paymentMethodTypes.add("card");

    Map<String, Object> params = new HashMap<>();

    params.put("amount",paymentInfo.getAmount());
    params.put("currency",paymentInfo.getCurrency());
    params.put("payment_method_types",paymentMethodTypes);
    params.put("description", "e-Commerce purchase");


    return PaymentIntent.create(params);
  }

  private String generateOrderTrackingNumber() {
    return UUID.randomUUID().toString();
  }
}
