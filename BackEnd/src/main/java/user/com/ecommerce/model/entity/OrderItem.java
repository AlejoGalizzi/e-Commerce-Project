package user.com.ecommerce.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "ORDER_ITEMS")
@Entity
@Getter
@Setter
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_item_id", nullable = false)
  private long id;

  @Column(name = "image_url")
  private String imageUrl;

  private Integer quantity;

  @Column(name = "unit_price")
  private Float unitPrice;

  @Column(name = "product_id")
  private long productId;

  @ManyToOne
  @JoinColumn(name = "ORDER_ID")
  private Order order;

}
