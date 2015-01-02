package gr.teonapster.eres.om;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author teonapster
 *	 House object model
 */

@Entity
@Table(name="DBHouse")
@XmlRootElement
public class House implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private int id;
	
	@ManyToMany(mappedBy = "houses")
	private List<Reservation> reservations;
	
	@NotNull
	@Column(nullable = false)
	@Basic(optional = false)
	private String name;
	
	@NotNull
	@Column(nullable = false)
	@Basic(optional = false)
	private String location;
	
	@Column(nullable = true)
	@Basic(optional = true)
	private String city;
	
	
	@Column(nullable = true)
	@Basic(optional = true)
	private String country;
	
	@NotNull
	@Column(nullable = false)
	@Basic(optional = false)
	private int rooms;
	
	
	@Column(nullable = true)
	@Basic(optional = true)
	private int beds;
	
	
	
	@Column(nullable = false)
	@Basic(optional = true)
	private int balcony;
	
	
	@Column(nullable = false)
	@Basic(optional = true)
	private int tv;
	
	
	@Column(nullable = false)
	@Basic(optional = true)
	private int ac;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getRooms() {
		return rooms;
	}

	public void setRooms(int rooms) {
		this.rooms = rooms;
	}

	public int getBeds() {
		return beds;
	}

	public void setBeds(int beds) {
		this.beds = beds;
	}

	public int getBalcony() {
		return balcony;
	}

	public void setBalcony(int balcony) {
		this.balcony = balcony;
	}

	public int getTv() {
		return tv;
	}

	public void setTv(int tv) {
		this.tv = tv;
	}

	public int getAc() {
		return ac;
	}

	public void setAc(int ac) {
		this.ac = ac;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	
	
	
}
