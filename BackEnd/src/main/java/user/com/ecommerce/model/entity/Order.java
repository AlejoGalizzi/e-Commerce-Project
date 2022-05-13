package user.com.ecommerce.model.entity;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Table(name = "ORDERS")
@Entity
@Data
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id", nullable = false)
  private long id;

  @Column(name = "order_tracking_number", nullable = false)
  private String orderTrackingNumber;

  @Column(name = "total_quantity", nullable = false)
  private Integer totalQuantity;

  @Column(name = "total_price", nullable = false)
  private float totalPrice;

  private String status;

  @Column(name = "date_created")
  @CreationTimestamp
  private Date dateCreated;

  @Column(name = "last_updated")
  @UpdateTimestamp
  private Date lastUpdated;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
  private Set<OrderItem> orderItems = new HashSet<>();

  @OneToOne
  @JoinColumn(name = "SHIPPING_ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
  private Address shippingAddress;

  @OneToOne
  @JoinColumn(name = "BILLING_ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
  private Address billingAddress;

  @ManyToOne
  @JoinColumn(name = "CUSTOMER_ID")
  private Customer customer;

  public void add(OrderItem item) {
    if (item != null) {
      if (orderItems == null) {
        orderItems = new HashSet<>();
      }
      orderItems.add(item);
      item.setOrder(this);
    }
  }
}
