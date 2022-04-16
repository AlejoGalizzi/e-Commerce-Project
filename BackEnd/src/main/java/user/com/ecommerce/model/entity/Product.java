package user.com.ecommerce.model.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "PRODUCT")
public class Product {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PRODUCT_ID", nullable = false)
  private Long productId;

  @Column(name = "SKU")
  private String sku;

  @Column(name = "NAME",nullable = false)
  private String name;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "UNIT_PRICE", nullable = false)
  private float unitPrice;

  @Column(name = "IMAGE_URL")
  private String imageUrl;

  @Column(name = "ACTIVE", nullable = false)
  private boolean active;

  @Column(name = "UNITS_IN_STOCK", nullable = false)
  private Integer unitsInStock;

  @ManyToOne
  @JoinColumn(name = "CATEGORY_ID")
  private Category category;

  @Column(name = "DATE_CREATED")
  @CreationTimestamp
  private Timestamp dateCreated;

  @Column(name = "LAST_UPDATED")
  @UpdateTimestamp
  private Timestamp dateUpdated;
}
