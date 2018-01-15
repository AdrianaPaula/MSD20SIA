package org.app.service.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class User_story implements Serializable {
	@Id @GeneratedValue
	 private Integer id_userstory;
	 private String  nume;
	 private String descriere;
	 
	 @ManyToOne
	 private Task task;
	 @ManyToOne
	 private Product_Backlog product_backlog;
	 
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public Product_Backlog getProduct_backlog() {
		return product_backlog;
	}
	public void setProduct_backlog(Product_Backlog product_backlog) {
		this.product_backlog = product_backlog;
	}
	public Integer getId_userstory() {
		return id_userstory;
	}
	public void setId_userstory(Integer id_userstory) {
		this.id_userstory = id_userstory;
	}
	public String getNume() {
		return nume;
	}
	public void setNume(String nume) {
		this.nume = nume;
	}
	public String getDescriere() {
		return descriere;
	}
	public void setDescriere(String descriere) {
		this.descriere = descriere;
	}
	public User_story() {
		super();
	}
	public User_story(Integer id_userstory, String nume, String descriere, Task task, Product_Backlog product_backlog) {
		super();
		this.id_userstory = id_userstory;
		this.nume = nume;
		this.descriere = descriere;
		this.task = task;
		this.product_backlog = product_backlog;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_userstory == null) ? 0 : id_userstory.hashCode());
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
		User_story other = (User_story) obj;
		if (id_userstory == null) {
			if (other.id_userstory != null)
				return false;
		} else if (!id_userstory.equals(other.id_userstory))
			return false;
		return true;
	}
	public User_story(Integer id_userstory, String nume, String descriere) {
		super();
		this.id_userstory = id_userstory;
		this.nume = nume;
		this.descriere = descriere;
	}

	 
}
