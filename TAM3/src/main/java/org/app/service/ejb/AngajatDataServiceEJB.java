package org.app.service.ejb;
import org.app.patterns.EntityRepository;
import org.app.patterns.EntityRepositoryBase;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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

import org.app.service.entities.Angajat;
import org.app.service.entities.Produs;
import org.app.service.entities.Task;
@Path("angajati")
@Stateless @LocalBean
public class AngajatDataServiceEJB extends EntityRepositoryBase<Angajat> implements AngajatDataService{
	private static Logger logger = Logger.getLogger(AngajatDataServiceEJB.class.getName());
	
	@EJB // injected DataService
	private TaskDataService taskDataService; 
	// Constructor
	private EntityRepository<Task> taskRepository;
	public AngajatDataServiceEJB() {
	}
	// Init after constructor
	@PostConstruct
	public void init(){
		taskRepository = new EntityRepositoryBase<Task>(this.em,Task.class);
		logger.info("POSTCONSTRUCT-INIT releaseRepository: " + this.taskRepository);
	}		
	/******** REST MAPPING IMPLEMENTATION ************************************************/

	@Override
	@GET 					
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Collection<Angajat> toCollection() {
		logger.info("**** DEBUG REST toCollection()");
		return super.toCollection();
	}
	
	@GET @Path("/{id}") 	
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	public Angajat getById(@PathParam("id") Integer id){ 
		Angajat angajat = super.getById(id);
		logger.info("**** DEBUG REST getById(" + id +") = " + angajat);
		return angajat;
	}	
	
	@POST 					/* MSD-S4/data/projects 		REST-resource: projects-collection*/
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	public Collection<Angajat> addIntoCollection(Angajat angajat) {
		// save aggregate
		super.add(angajat);
		logger.info("**** DEBUG REST save aggregate POST");
		// return updated collection
		return super.toCollection();
	}
	
	@PUT @Path("/{id}") 		
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	@Override
	public Angajat add(Angajat angajat) {
		// restore aggregation-relation
		for (Task a: angajat.getTask())
			a.setAngajat(angajat);
		logger.info("**** DEBUG REST restore aggregation-relation PUT");
		// save aggregate
		logger.info("**** DEBUG REST save aggregate PUT");
		angajat = super.add(angajat);
		// return updated collection	
		return angajat;
	}
	
	@DELETE 				/* MSD-S4/data/projects 		REST-resource: projects-collection*/
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	public Collection<Angajat> removeFromCollection(Angajat angajat) {
		logger.info("DEBUG: called REMOVE - angajat: " + angajat);
		super.remove(angajat);
		return super.toCollection();
	}
	
	@DELETE @Path("/{id}") 	/* MSD-S4/data/projects/{id} 	REST-resource: project-entity*/	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	public void remove(@PathParam("id")Integer id) {
		logger.info("DEBUG: called REMOVE - ById() for angajati >>>>>>>>>>>>>> simplified ! + id");
		Angajat angajat = super.getById(id);
		super.remove(angajat);
	}
	
	@GET @Path("/{projectid}/releases/{releaseid}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Task getTaskById(@PathParam("releaseid") Integer id_task){
		logger.info("DEBUG: called getReleaseById() for projects >>>>>>>>>>>>>> simplified !");
		Task task = taskRepository.getById(id_task);
		return task;
	}
	@POST @Path("/new/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	// Aggregate factory method
	public Angajat createNewAngajat(@PathParam("id")Integer id){
		logger.info("**** DEBUG REST createNewAngajat POST");
		// create project aggregate
		Angajat angajat = new Angajat(id, "Popescu Maria" + "." + id);
		List<Task> taskAngajat = new ArrayList<>();
		Integer taskCount = 3;
		for (int i=0; i<=taskCount-1; i++){
			taskAngajat.add(new Task(null, "Create user stories " +  "." + " "));
		}
		angajat.setTask(taskAngajat);		
		// save project aggregate
		this.add(angajat);
		// return project aggregate to service client
		return angajat;
	}
	
	@GET @Path("/test") // Check if resource is up ...
	@Produces({ MediaType.TEXT_PLAIN})
	public String getMessage(){
		return "Project DataService is working...";
	}	
	
	// dummy XML marshall Rest: http://localhost:8080/MSD-S4/data/projects/projectdata
	@GET @Path("/projectdata")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getProjectData() throws Exception{
		Angajat dto = new Angajat(1111, "Pro 1111");
		JAXBContext jaxbContext = JAXBContext.newInstance(Angajat.class);
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
	public Angajat addAngajat(Angajat AngajatToAdd) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String removeAngajat(Angajat AngajatToDelete) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Angajat getAngajatByID(Integer id_angajat) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Collection<Angajat> getAngajati() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Angajat getAngajatByDenumire(String nume) {
		// TODO Auto-generated method stub
		return null;
	}
	

}


