package org.app.service.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Task implements Serializable{
	
	@Id @GeneratedValue
  private Integer id_task;
  private String nume_task;
  private String descriere;
  private String termen_limita;
  
	 
   @OneToMany
     List<User_story> user_stories= new ArrayList<User_story>();
		 
   @ManyToOne
  private Angajat angajat;
   
   
public Integer getId_task() {
	return id_task;
}
public void setId_task(Integer id_task) {
	this.id_task = id_task;
}
public String getNume_task() {
	return nume_task;
}
public void setNume_task(String nume_task) {
	this.nume_task = nume_task;
}
public String getDescriere() {
	return descriere;
}
public void setDescriere(String descriere) {
	this.descriere = descriere;
}
public String getTermen_limita() {
	return termen_limita;
}
public void setTermen_limita(String termen_limita) {
	this.termen_limita = termen_limita;
}
public Task() {
	super();
}
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
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id_task == null) ? 0 : id_task.hashCode());
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
	Task other = (Task) obj;
	if (id_task == null) {
		if (other.id_task != null)
			return false;
	} else if (!id_task.equals(other.id_task))
		return false;
	return true;
}
public Task(Integer id_task, String nume_task, String descriere, String termen_limita) {
	super();
	this.id_task = id_task;
	this.nume_task = nume_task;
	this.descriere = descriere;
	this.termen_limita = termen_limita;
}



  
  
}
