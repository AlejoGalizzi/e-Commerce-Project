package user.com.ecommerce.model.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Table(name = "CUSTOMERS")
@Entity
@Data
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "customer_id",nullable = false)
  private long id;

  @Column(name = "first_name",nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  private String email;
}
