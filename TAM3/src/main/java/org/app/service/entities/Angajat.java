package org.app.service.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Angajat implements Serializable {
	@Id @GeneratedValue
	private Integer id_angajat;
	private String nume;
	private String Adresa;
	 @OneToMany(mappedBy="angajat")
	 List<Task> task= new ArrayList<Task>();
	
	public Integer getId_angajat() {
		return id_angajat;
	}
	public void setId_angajat(Integer id_angajat) {
		this.id_angajat = id_angajat;
	}
	public String getNume() {
		return nume;
	}
	public void setNume(String nume) {
		this.nume = nume;
	}
	public String getAdresa() {
		return Adresa;
	}
	public void setAdresa(String adresa) {
		Adresa = adresa;
	}
	
	
	public Angajat(Integer id_angajat, String nume, String adresa, List<Task> task) {
		super();
		this.id_angajat = id_angajat;
		this.nume = nume;
		Adresa = adresa;
		this.task = task;
	}
	public Angajat() {
		super();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_angajat == null) ? 0 : id_angajat.hashCode());
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
		Angajat other = (Angajat) obj;
		if (id_angajat == null) {
			if (other.id_angajat != null)
				return false;
		} else if (!id_angajat.equals(other.id_angajat))
			return false;
		return true;
	}
	public Angajat(Integer id_angajat, String nume, String adresa) {
		super();
		this.id_angajat = id_angajat;
		this.nume = nume;
		Adresa = adresa;
	}

	

}
