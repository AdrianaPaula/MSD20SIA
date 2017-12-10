package org.app.service.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Produs implements Serializable{
	@Id
	@GeneratedValue
	private Integer id_produs;
	private String denumire;
	private String observatii;
	
	
	
	//@OneToMany(mappedBy="produs")
	// List<Product_Backlog> product_backlog= new ArrayList<Product_Backlog>();
	
	public Integer getId_produs() {
		return id_produs;
	}
	public void setId_produs(Integer id_produs) {
		this.id_produs = id_produs;
	}
	public String getDenumire() {
		return denumire;
	}
	public void setDenumire(String denumire) {
		this.denumire = denumire;
	}
	public String getObservatii() {
		return observatii;
	}
	public void setObservatii(String observatii) {
		this.observatii = observatii;
	}
	
	public Produs() {
		super();
	}
	public Produs(Integer id_produs, String denumire, String observatii) {
		super();
		this.id_produs = id_produs;
		this.denumire = denumire;
		this.observatii = observatii;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_produs == null) ? 0 : id_produs.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produs other = (Produs) obj;
		if (id_produs == null) {
			if (other.id_produs != null)
				return false;
		} else if (!id_produs.equals(other.id_produs))
			return false;
		return true;
	}
	
	

}
