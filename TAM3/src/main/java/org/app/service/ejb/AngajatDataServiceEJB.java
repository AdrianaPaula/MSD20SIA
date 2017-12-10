package org.app.service.ejb;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.app.service.entities.Angajat;
import org.app.service.entities.Produs;

@Stateless @LocalBean
public class AngajatDataServiceEJB implements AngajatDataService{
	private static Logger logger = Logger.getLogger(AngajatDataServiceEJB.class.getName());
	
	/* DataService initialization */
	// Inject resource 
	@PersistenceContext(unitName="MSD")
	private EntityManager em;
	// Constructor
	public AngajatDataServiceEJB() {
	}
	// Init after constructor
	@PostConstruct
	public void init(){
		logger.info("POSTCONSTRUCT-INIT : " + this.em);
	}		

	/* CRUD operations implementation */
	// CREATE or UPDATE
	@Override
	public Angajat addAngajat(Angajat AngajatToAdd){
		em.persist(AngajatToAdd);
		em.flush();
		// transactions are managed by default by container
		em.refresh(AngajatToAdd);
		return AngajatToAdd;
	}	
	// READ
	@Override
	public Angajat getAngajatByID(Integer id_angajat) {
		return em.find(Angajat.class, id_angajat);
	}	
	public Collection<Angajat> getAngajati(){
		List<Angajat> angajati = em.createQuery("SELECT a FROM Angajat a", Angajat.class).getResultList();
		return angajati;
	}
	// REMOVE
	@Override
	public String removeAngajat(Angajat AngajatToDelete){
		AngajatToDelete = em.merge(AngajatToDelete);
		em.remove(AngajatToDelete);
		em.flush();
		return "True";
	}
	
	// Custom READ: custom query
	@Override
	public Angajat getAngajatByDenumire(String nume) {
		return em.createQuery("SELECT a FROM Angajat a WHERE a.nume = :nume", Angajat.class)
				.setParameter("denumire", nume)
				.getSingleResult();
	}	
	
	// Others
	public String getMessage() {
		return "ProdusServiceEJB is ON... ";
	}
}


