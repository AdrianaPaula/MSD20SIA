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
import org.app.service.entities.Task;

@Stateless @LocalBean
public class TaskDataServiceEJB implements TaskDataService{
	private static Logger logger = Logger.getLogger(TaskDataServiceEJB.class.getName());
	
	/* DataService initialization */
	// Inject resource 
	@PersistenceContext(unitName="MSD")
	private EntityManager em;
	// Constructor
	public TaskDataServiceEJB() {
	}
	// Init after constructor
	@PostConstruct
	public void init(){
		logger.info("POSTCONSTRUCT-INIT : " + this.em);
	}		

	/* CRUD operations implementation */
	// CREATE or UPDATE
	@Override
	public Task addTask(Task TaskToAdd){
		em.persist(TaskToAdd);
		em.flush();
		// transactions are managed by default by container
		em.refresh(TaskToAdd);
		return TaskToAdd;
	}	
	// READ
	@Override
	public Task getTaskByID(Integer id_task) {
		return em.find(Task.class, id_task);
	}	
	public Collection<Task> getTaskuri(){
		List<Task> taskuri = em.createQuery("SELECT t FROM Task t", Task.class).getResultList();
		return taskuri;
	}
	// REMOVE
	@Override
	public String removeTask(Task TaskToDelete){
		TaskToDelete = em.merge(TaskToDelete);
		em.remove(TaskToDelete);
		em.flush();
		return "True";
	}
	
	// Custom READ: custom query
	@Override
	public Task getTaskByNume_Task(String nume_task) {
		return em.createQuery("SELECT t FROM Task t WHERE t.nume_task = :nume_task", Task.class)
				.setParameter("nume_task", nume_task)
				.getSingleResult();
	}	
	
	// Others
	public String getMessage() {
		return "TaskServiceEJB is ON... ";
	}
}


