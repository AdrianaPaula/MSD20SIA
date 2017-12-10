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
import org.app.service.entities.User_story;

@Stateless @LocalBean
public class User_storyDataServiceEJB implements User_storyDataService{
	private static Logger logger = Logger.getLogger(AngajatDataServiceEJB.class.getName());
	
	/* DataService initialization */
	// Inject resource 
	@PersistenceContext(unitName="MSD")
	private EntityManager em;
	// Constructor
	public User_storyDataServiceEJB() {
	}
	// Init after constructor
	@PostConstruct
	public void init(){
		logger.info("POSTCONSTRUCT-INIT : " + this.em);
	}		

	/* CRUD operations implementation */
	// CREATE or UPDATE
	@Override
	public User_story addUser_story(User_story User_storyToAdd){
		em.persist(User_storyToAdd);
		em.flush();
		// transactions are managed by default by container
		em.refresh(User_storyToAdd);
		return User_storyToAdd;
	}	
	// READ
	@Override
	public User_story getUser_storyByID(Integer id_userstory) {
		return em.find(User_story.class, id_userstory);
	}	
	public Collection<User_story> getUser_stories(){
		List<User_story> user_stories = em.createQuery("SELECT u FROM User_story u", User_story.class).getResultList();
		return user_stories;
	}
	// REMOVE
	@Override
	public String removeUser_story(User_story User_storyToDelete){
		User_storyToDelete = em.merge(User_storyToDelete);
		em.remove(User_storyToDelete);
		em.flush();
		return "True";
	}
	
	// Custom READ: custom query
	@Override
	public User_story getUser_storyByNume(String nume) {
		return em.createQuery("SELECT u FROM User_story u WHERE u.nume = :nume", User_story.class)
				.setParameter("denumire", nume)
				.getSingleResult();
	}	
	
	// Others
	public String getMessage() {
		return "ProdusServiceEJB is ON... ";
	}
}


