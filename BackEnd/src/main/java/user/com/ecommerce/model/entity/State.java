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

@Table(name = "STATES")
@Entity
@Getter
@Setter
public class State {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "STATE_ID", nullable = false)
  private long id;

  @Column(name = "NAME", unique = true)
  private String name;

  @ManyToOne
  @JoinColumn(name = "COUNTRY_ID")
  private Country country;
}
