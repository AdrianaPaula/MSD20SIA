package org.app.service.ejb;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.app.patterns.EntityRepository;
import org.app.patterns.EntityRepositoryBase;
import org.app.service.entities.Produs;
import org.app.service.entities.Task;
import org.app.service.entities.User_story;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
@Path("tasks") 
@Stateless @LocalBean
public class TaskDataServiceEJB 
		extends EntityRepositoryBase<Task>
		implements TaskDataService {
	private static Logger logger = Logger.getLogger(TaskDataServiceEJB.class.getName());
			
//	@EJB // injected DataService
//	private FeatureDataService featureDataService; 
	// Local component-entity-repository
	private EntityRepository<User_story> user_storyRepository;
	@PostConstruct
	public void init() {
		// local instantiation of local component-entity-repository
		user_storyRepository = new EntityRepositoryBase<User_story>(this.em,User_story.class);
		logger.info("POSTCONSTRUCT-INIT releaseRepository: " + this.user_storyRepository);
		//logger.info("POSTCONSTRUCT-INIT featureDataService: " + this.featureDataService);
	}
	
	/******** REST MAPPING IMPLEMENTATION ************************************************/

	@Override
	@GET 					/* MSD-S4/data/projects 		REST-resource: projects-collection*/
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Collection<Task> toCollection() {
		logger.info("**** DEBUG REST toCollection()");
		return super.toCollection();
	}	
	
	
	@GET @Path("/{id}") 	/* MSD-S4/data/projects/data/{id} 	REST-resource: project-entity*/
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	public Task getById(@PathParam("id") Integer id){ 
		Task task = super.getById(id);
		logger.info("**** DEBUG REST getById(" + id +") = " + task);
		return task;
	}	
	
	@POST 					/* MSD-S4/data/projects 		REST-resource: projects-collection*/
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	public Collection<Task> addIntoCollection(Task task) {
		// save aggregate
		super.add(task);
		logger.info("**** DEBUG REST save aggregate POST");
		// return updated collection
		return super.toCollection();
	}
	
	@PUT @Path("/{id}") 	/* MSD-S4/data/projects/{id} 	REST-resource: project-entity*/	
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	@Override
	public Task add(Task task) {
		// restore aggregation-relation
		for (User_story u: task.getUser_stories())
			u.setTask(task);
		logger.info("**** DEBUG REST restore aggregation-relation PUT");
		// save aggregate
		logger.info("**** DEBUG REST save aggregate PUT");
		task= super.add(task);
		// return updated collection	
		return task;
	}	
	
//	@Override
	@DELETE 				/* MSD-S4/data/projects 		REST-resource: projects-collection*/
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	public Collection<Task> removeFromCollection(Task task) {
		logger.info("DEBUG: called REMOVE - project: " + task);
		super.remove(task);
		return super.toCollection();
	}
	
	@DELETE @Path("/{id}") 	/* MSD-S4/data/projects/{id} 	REST-resource: project-entity*/	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	public void remove(@PathParam("id")Integer id) {
		logger.info("DEBUG: called REMOVE - ById() for projects >>>>>>>>>>>>>> simplified ! + id");
		Task task = super.getById(id);
		super.remove(task);
	}
	
	// GET method on second repository for Release-type entities
	@GET @Path("/{projectid}/releases/{releaseid}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public User_story getReleaseById(@PathParam("releaseid") Integer id_userstory){
		logger.info("DEBUG: called getReleaseById() for projects >>>>>>>>>>>>>> simplified !");
		User_story user_story = user_storyRepository.getById(id_userstory);
		return user_story;
	}
	
	/* Other test-proposal methods ************************************************************/
	@POST @Path("/new/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	// Aggregate factory method
	public Task addTask(@PathParam("id")Integer id){
		logger.info("**** DEBUG REST createNewTask POST");
		// create project aggregate
		Task task = new Task(id, "NEW Project" + "." + id);
		List<User_story> user_storiesTask = new ArrayList<>();
		
		Integer user_storyCount = 3;
		for (int i=0; i<=user_storyCount-1; i++){
			user_storiesTask.add(new User_story(null, "R: " + "Taskul" + "With ", task.getDescriere()));
		}
		task.setUser_stories(user_storiesTask);		
		// save project aggregate
		this.add(task);
		// return project aggregate to service client
		return task;
	}
	
	@GET @Path("/test") // Check if resource is up ...
	@Produces({ MediaType.TEXT_PLAIN})
	public String getMessage(){
		return "Project DataService is working...";
	}	
	
	// dummy XML marshall Rest: http://localhost:8080/MSD-S4/data/projects/projectdata
	@GET @Path("/taskdata")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getProjectData() throws Exception{
		Task dto = new Task(1111, "Pro 1111");
		JAXBContext jaxbContext = JAXBContext.newInstance(Task.class);
		Marshaller marshaller = jaxbContext.createMarshaller();
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		marshaller.marshal(dto, os);
		String aString = new String(os.toByteArray(),"UTF-8");
		
		Response response = Response
				.status(Status.OK)
				.type(MediaType.TEXT_PLAIN)
				.entity(aString)
				.build();
		
		return response;
	}

	@Override
	public Task addTask(Task TaskToAdd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String removeTask(Task TaskToDelete) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task getTaskByID(Integer id_task) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Task> getTaskuri() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task getTaskByNume_Task(String nume_task) {
		// TODO Auto-generated method stub
		return null;
	}
}


