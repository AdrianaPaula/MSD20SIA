package org.app.service.entities;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
@XmlRootElement(name="angajati") 
@XmlAccessorType(XmlAccessType.NONE)
@Entity
public class Angajat implements Serializable {
	@Id @GeneratedValue @NotNull
	private Integer id_angajat;
	private String nume;
	private String Adresa;

	@OneToMany(mappedBy="angajat", cascade = ALL, fetch = EAGER, orphanRemoval = false)
	 List<Task> task= new ArrayList<Task>();
	
	@XmlElement
	public Integer getId_angajat() {
		return id_angajat;
	}
	public void setId_angajat(Integer id_angajat) {
		this.id_angajat = id_angajat;
	}
	@XmlElement
	public String getNume() {
		return nume;
	}
	public void setNume(String nume) {
		this.nume = nume;
	}
	@XmlElement
	public String getAdresa() {
		return Adresa;
	}
	public void setAdresa(String adresa) {
		Adresa = adresa;
	}
	
	@XmlElementWrapper(name = "taskuri") @XmlElement(name = "taskuri")
	public List<Task> getTask() {
		return task;
	}
	public void setTask(List<Task> task) {
		this.task = task;
	}
	
	
	public static String BASE_URL = "http://localhost:8080/TAM3/resources/angajati";
	@XmlElement(name = "link")
    public AtomLink getLink() throws Exception {
		String restUrl = BASE_URL + this.getId_angajat();
        return new AtomLink(restUrl, "get-angajat");
    }	
	
	public void setLink(AtomLink link){}
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

	public Angajat(Integer id_angajat, String nume) {
		super();
		this.id_angajat = id_angajat;
		this.nume = nume;
	}
	public int compareTo(Angajat other) {
		if (this.equals(other))
			return 0;
		return this.getNume().compareTo(other.getNume());
	}
	@Override
	public String toString() {
		return "Angajat [id_angajat=" + id_angajat + ", nume=" + nume + ", Adresa=" + Adresa + ", task=" + task + "]";
	}
	
	
}
