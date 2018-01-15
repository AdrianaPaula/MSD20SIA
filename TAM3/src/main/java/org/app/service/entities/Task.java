package org.app.service.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlRootElement(name="task") 
@XmlAccessorType(XmlAccessType.NONE)
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Task implements Comparable<Task>, Serializable{
	
	@Id @GeneratedValue @NotNull
  private Integer id_task;
  private String nume_task;
  private String descriere;
  private String termen_limita;
   @Transient
   @OneToMany
     List<User_story> user_stories= new ArrayList<User_story>();
	@Enumerated	 
	@ManyToOne
  private Angajat angajat;
   
	@XmlElement
public Integer getId_task() {
	return id_task;
}
public void setId_task(Integer id_task) {
	this.id_task = id_task;
}
@XmlElement
public String getNume_task() {
	return nume_task;
}
public void setNume_task(String nume_task) {
	this.nume_task = nume_task;
}
@XmlElement
public String getDescriere() {
	return descriere;
}
public void setDescriere(String descriere) {
	this.descriere = descriere;
}
@XmlElement
public String getTermen_limita() {
	return termen_limita;
}
public void setTermen_limita(String termen_limita) {
	this.termen_limita = termen_limita;
}
@XmlElementWrapper(name="user_stories") @XmlElement(name="user_stories")
public List<User_story> getUser_stories() {
	return user_stories;
}
public void setUser_stories(List<User_story> user_stories) {
	this.user_stories = user_stories;
}
public Angajat getAngajat() {
	return angajat;
}
public void setAngajat(Angajat angajat) {
	this.angajat = angajat;
}
public Task() {
	super();
}

public static String BASE_URL = "http://localhost:8080/TAM3/resources/task";
@XmlElement(name = "link")
public AtomLink getLink() throws Exception {
	String restUrl = BASE_URL + this.getId_task();
    return new AtomLink(restUrl, "get-task");
}	

public void setLink(AtomLink link){}
public Task(Integer id_task, String nume_task, String descriere, String termen_limita, List<User_story> user_stories,
		Angajat angajat) {
	super();
	this.id_task = id_task;
	this.nume_task = nume_task;
	this.descriere = descriere;
	this.termen_limita = termen_limita;
	this.user_stories = user_stories;
	this.angajat = angajat;
}

public Task(Integer id_task, String nume_task, String descriere) {
	super();
	this.id_task = id_task;
	this.nume_task = nume_task;
	this.descriere = descriere;
	
}

public enum StatusTask {
	
	Progress,
	Completed,
	Blocked,
	Finished;

}
@Override
public int compareTo(Task other) {
	if (this.equals(other))
		return 0;
	return this.getNume_task().compareTo(other.getNume_task());
}
public Task(Integer id_task, String nume_task) {
	super();
	this.id_task = id_task;
	this.nume_task = nume_task;
	
	
}
@Override
public String toString() {
	return "Task [id_task=" + id_task + ", nume_task=" + nume_task + ", descriere=" + descriere + ", termen_limita="
			+ termen_limita + ", user_stories=" + user_stories + ", angajat=" + angajat + "]";
}
  
}
