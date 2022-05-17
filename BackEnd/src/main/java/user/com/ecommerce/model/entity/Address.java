package user.com.ecommerce.repository.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Data;

@Table(name = "ADDRESSES")
@Entity
@Data
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "address_id", nullable = false)
  private long id;

  private String city;
  private String country;
  private String state;
  private String street;

  @Column(name = "zip_code")
  private String zipCode;

  @OneToOne
  @PrimaryKeyJoinColumn
  private Order order;
}
