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
import org.app.service.entities.EntityBase;

import org.app.service.entities.Produs;


@Remote
public interface ProdusDataService{
	// CREATE or UPDATE
	Produs addProdus(Produs ProdusToAdd);

	// DELETE
	String removeProdus(Produs ProdusToDelete);

	// READ
	Produs getProdusByID(Integer id_produs);
	Collection<Produs> getProduse();
	
	// Custom READ: custom query
	Produs getProdusByDenumire(String denumire);
	
	// Others
	String getMessage();
}

