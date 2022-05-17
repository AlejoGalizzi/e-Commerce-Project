package user.com.ecommerce.repository.model.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "COUNTRIES")
@Entity
@Getter
@Setter
public class Country {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "COUNTRY_ID", nullable = false)
  private long id;

  @Column(name = "CODE")
  private String code;

  @Column(name = "NAME")
  private String name;

  @OneToMany(mappedBy = "country")
  private List<State> states;
}
