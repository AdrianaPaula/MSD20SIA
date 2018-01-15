package org.app.service.ejb;

import java.util.Collection;
import java.util.List;

import javax.ejb.Remote;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.app.patterns.EntityRepository;
import org.app.service.entities.Angajat;
import org.app.service.entities.EntityBase;
import org.app.service.entities.Product_Backlog;
import org.app.service.entities.Produs;
import org.app.service.entities.User_story;


@Remote
public interface Product_BacklogDataService{
	// CREATE or UPDATE
	Product_Backlog addProduct_Backlog(Product_Backlog Product_BacklogToAdd);

	// DELETE
	String removeProduct_Backlog(Product_Backlog Product_BacklogToDelete);

	// READ
	Product_Backlog getProduct_BacklogByID(Integer cod);
	Collection<Product_Backlog> getProduct_Backlogs();
	User_story getUser_storyById(Integer id_userstory);
	// Custom READ: custom query
	Product_Backlog getProduct_BacklogByDenumire(String Detalii);
	
	// Others
	String getMessage();
}

