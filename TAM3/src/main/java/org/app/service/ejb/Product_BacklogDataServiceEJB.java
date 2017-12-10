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
import org.app.service.entities.Product_Backlog;
import org.app.service.entities.Produs;

@Stateless @LocalBean
public class Product_BacklogDataServiceEJB implements Product_BacklogDataService{
	private static Logger logger = Logger.getLogger(Product_BacklogDataServiceEJB.class.getName());
	
	/* DataService initialization */
	// Inject resource 
	@PersistenceContext(unitName="MSD")
	private EntityManager em;
	// Constructor
	public Product_BacklogDataServiceEJB() {
	}
	// Init after constructor
	@PostConstruct
	public void init(){
		logger.info("POSTCONSTRUCT-INIT : " + this.em);
	}		

	/* CRUD operations implementation */
	// CREATE or UPDATE
	@Override
	public Product_Backlog addProduct_Backlog(Product_Backlog Product_BacklogToAdd){
		em.persist(Product_BacklogToAdd);
		em.flush();
		// transactions are managed by default by container
		em.refresh(Product_BacklogToAdd);
		return Product_BacklogToAdd;
	}	
	// READ
	@Override
	public Product_Backlog getProduct_BacklogByID(Integer cod) {
		return em.find(Product_Backlog.class, cod);
	}	
	public Collection<Product_Backlog> getProduct_Backlogs(){
		List<Product_Backlog> product_backlogs = em.createQuery("SELECT p FROM Product_Backlog p", Product_Backlog.class).getResultList();
		return product_backlogs;
	}
	// REMOVE
	@Override
	public String removeProduct_Backlog(Product_Backlog Product_BacklogToDelete){
		Product_BacklogToDelete = em.merge(Product_BacklogToDelete);
		em.remove(Product_BacklogToDelete);
		em.flush();
		return "True";
	}
	
	// Custom READ: custom query
	@Override
	public Product_Backlog getProduct_BacklogByDenumire(String Detalii) {
		return em.createQuery("SELECT p FROM Product_Backlog p WHERE p.detalii = :detalii", Product_Backlog.class)
				.setParameter("denumire", Detalii)
				.getSingleResult();
	}	
	
	// Others
	public String getMessage() {
		return "ProdusServiceEJB is ON... ";
	}
}


