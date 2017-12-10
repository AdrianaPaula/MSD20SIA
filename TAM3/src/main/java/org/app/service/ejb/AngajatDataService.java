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

import org.app.service.entities.Produs;


@Remote
public interface AngajatDataService{
	// CREATE or UPDATE
	Angajat addAngajat(Angajat AngajatToAdd);

	// DELETE
	String removeAngajat(Angajat AngajatToDelete);

	// READ
	Angajat getAngajatByID(Integer id_angajat);
	Collection<Angajat> getAngajati();
	
	// Custom READ: custom query
	Angajat getAngajatByDenumire(String nume);
	
	// Others
	String getMessage();
}

