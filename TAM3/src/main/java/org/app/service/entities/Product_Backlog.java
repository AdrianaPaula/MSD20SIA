package org.app.service.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Product_Backlog implements Serializable {
@Id
@GeneratedValue
	private Integer cod;
	private String Detalii;
	@OneToMany(mappedBy="product_backlog")
	 List<User_story> user_stories= new ArrayList<User_story>();
	 @ManyToOne
		private Produs produs;
	
	public Integer getCod() {
		return cod;
	}
	public void setCod(Integer cod) {
		this.cod = cod;
	}
	public String getDetalii() {
		return Detalii;
	}
	public void setDetalii(String detalii) {
		Detalii = detalii;
	}
	
	public Product_Backlog() {
		super();
	}
	public Product_Backlog(Integer cod, String detalii, List<User_story> user_stories, Produs produs) {
		super();
		this.cod = cod;
		Detalii = detalii;
		this.user_stories = user_stories;
		this.produs = produs;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cod == null) ? 0 : cod.hashCode());
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
		Product_Backlog other = (Product_Backlog) obj;
		if (cod == null) {
			if (other.cod != null)
				return false;
		} else if (!cod.equals(other.cod))
			return false;
		return true;
	}
	public Product_Backlog(Integer cod, String detalii) {
		super();
		this.cod = cod;
		Detalii = detalii;
		
	}
	
	
}
