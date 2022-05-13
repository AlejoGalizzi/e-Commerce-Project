package user.com.ecommerce.model.entity;


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Table(name = "CUSTOMERS")
@Entity
@Data
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "customer_id", nullable = false)
  private long id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  private String email;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  private Set<Order> orders = new HashSet<>();

  public void add(Order order) {
    if (order != null) {
      if (orders == null) {
        orders = new HashSet<>();
      }
      orders.add(order);
      order.setCustomer(this);
    }
  }
}
