package user.com.ecommerce.service;

import java.util.Set;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.com.ecommerce.dto.Purchase;
import user.com.ecommerce.model.entity.Customer;
import user.com.ecommerce.model.entity.Order;
import user.com.ecommerce.model.entity.OrderItem;
import user.com.ecommerce.model.response.PurchaseResponse;
import user.com.ecommerce.repository.ICustomerRepository;
import user.com.ecommerce.repository.IOrderRepository;
import user.com.ecommerce.service.abstraction.ICheckout;

@Service
public class CheckoutService implements ICheckout {

  @Autowired
  private ICustomerRepository customerRepository;

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
    customer.add(order);

    customerRepository.save(customer);

    return new PurchaseResponse(orderTrackingNumber);
  }

  private String generateOrderTrackingNumber() {
    return UUID.randomUUID().toString();
  }
}
