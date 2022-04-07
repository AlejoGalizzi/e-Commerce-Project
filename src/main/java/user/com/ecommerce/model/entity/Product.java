package user.com.ecommerce.model.entity;

import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Entity
@Table(name = "PRODUCT")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "SLIDE_ID", nullable = false)
  private Long productoId;

  @Column(name = "SKU")
  private String sku;

  @Column(name = "NAME")
  @NotBlank
  private String name;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "UNIT_PRICE")
  @NotBlank
  private float unitPrice;

  @Column(name = "IMAGE_URL")
  private String imageUrl;

  @Column(name = "ACTIVE")
  @NotBlank
  private boolean active;

  @Column(name = "UNITS_IN_STOCK")
  @NotBlank
  private Integer unitsInStock;

  @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
  @JoinColumn(name = "CATEGORY_ID")
  @NotBlank
  private Category category;

  @Column(name = "DATE_CREATED")
  @CreationTimestamp
  @NotBlank
  private Timestamp dateCreated;

  @Column(name = "LAST_UPDATED")
  @CreationTimestamp
  @NotBlank
  private Timestamp dateUpdated;
}
