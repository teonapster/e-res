package gr.teonapster.eres.om;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "DBReservation")
@XmlRootElement
public class Reservation implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private int id;
	
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	private User user;
	
	@ManyToMany
	  @JoinTable(
	      name="RES_HOUSE",
	      joinColumns={@JoinColumn(name="id", referencedColumnName="id")},
	      inverseJoinColumns={@JoinColumn(name="id", referencedColumnName="id")})
	private List<House> houses;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<House> getHouses() {
		return houses;
	}

	public void setHouses(List<House> houses) {
		this.houses = houses;
	}
	
	
}
