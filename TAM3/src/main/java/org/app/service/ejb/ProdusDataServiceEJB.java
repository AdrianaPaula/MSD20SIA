package org.app.service.ejb;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.app.service.entities.Produs;

@Stateless @LocalBean
public class ProdusDataServiceEJB implements ProdusDataService{
	private static Logger logger = Logger.getLogger(ProdusDataServiceEJB.class.getName());
	
	/* DataService initialization */
	// Inject resource 
	@PersistenceContext(unitName="MSD")
	private EntityManager em;
	// Constructor
	public ProdusDataServiceEJB() {
	}
	// Init after constructor
	@PostConstruct
	public void init(){
		logger.info("POSTCONSTRUCT-INIT : " + this.em);
	}		

	/* CRUD operations implementation */
	// CREATE or UPDATE
	@Override
	public Produs addProdus(Produs ProdusToAdd){
		em.persist(ProdusToAdd);
		em.flush();
		// transactions are managed by default by container
		em.refresh(ProdusToAdd);
		return ProdusToAdd;
	}	
	// READ
	@Override
	public Produs getProdusByID(Integer id_produs) {
		return em.find(Produs.class, id_produs);
	}	
	public Collection<Produs> getProduse(){
		List<Produs> produse = em.createQuery("SELECT p FROM Produs p", Produs.class).getResultList();
		return produse;
	}
	// REMOVE
	@Override
	public String removeProdus(Produs ProdusToDelete){
		ProdusToDelete = em.merge(ProdusToDelete);
		em.remove(ProdusToDelete);
		em.flush();
		return "True";
	}
	
	// Custom READ: custom query
	@Override
	public Produs getProdusByDenumire(String denumire) {
		return em.createQuery("SELECT p FROM Produs p WHERE p.denumire = :denumire", Produs.class)
				.setParameter("denumire", denumire)
				.getSingleResult();
	}	
	
	// Others
	public String getMessage() {
		return "ProdusServiceEJB is ON... ";
	}
}


