package user.com.ecommerce.model.entity;

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
import lombok.Getter;
import lombok.Setter;

@Table(name = "CATEGORY")
@Entity
@Setter
@Getter
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "CATEGORY_ID", nullable = false)
  private long id;

  @Column(name = "NAME", unique = true, nullable = false)
  private String name;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
  private Set<Product> products;
}
