package gr.teonapster.eres.om;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "DBReservation")
@XmlRootElement
public class Reservation implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private int reservationId;
	
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	private User user;
	
	@NotNull
	@Column(nullable = false)
	@Basic(optional = false)
	@Temporal(TemporalType.DATE)
	private Date start;
	
	@NotNull
	@Column(nullable = false)
	@Basic(optional = false)
	@Temporal(TemporalType.DATE)
	private Date until;
	
	@ManyToMany
    @JoinTable(name="RES_HOUSE",
                joinColumns=
                     @JoinColumn(name="reservationId"),
                inverseJoinColumns=
                     @JoinColumn(name="houseId")
    )
	private Collection<House> houses;
	

	@XmlTransient
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@XmlTransient
	public Collection<House> getHouses() {
		return houses;
	}

	public void setHouses(Collection<House> houses) {
		this.houses = houses;
	}

	public int getId() {
		return reservationId;
	}

	public void setId(int id) {
		this.reservationId = id;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date from) {
		this.start = from;
	}

	public Date getUntil() {
		return until;
	}

	public void setUntil(Date until) {
		this.until = until;
	}
	
	
}
