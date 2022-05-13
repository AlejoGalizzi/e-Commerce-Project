package user.com.ecommerce.dto;

import java.util.Set;
import lombok.Data;
import user.com.ecommerce.model.entity.Address;
import user.com.ecommerce.model.entity.Customer;
import user.com.ecommerce.model.entity.Order;
import user.com.ecommerce.model.entity.OrderItem;

@Data
public class Purchase {

  private Customer customer;
  private Address shippingAddress;
  private Address billingAddress;
  private Order order;
  private Set<OrderItem> orderItems;
}
