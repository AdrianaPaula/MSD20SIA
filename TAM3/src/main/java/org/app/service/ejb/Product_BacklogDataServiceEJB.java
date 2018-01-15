package org.app.service.ejb;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
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

import org.app.patterns.EntityRepository;
import org.app.patterns.EntityRepositoryBase;
import org.app.service.entities.Angajat;
import org.app.service.entities.Product_Backlog;
import org.app.service.entities.Produs;
import org.app.service.entities.User_story;
@Path("product_backlogs")
@Stateless @LocalBean
public class Product_BacklogDataServiceEJB extends EntityRepositoryBase<Product_Backlog> implements Product_BacklogDataService{
	private static Logger logger = Logger.getLogger(Product_BacklogDataServiceEJB.class.getName());
	
	/* DataService initialization */
	// Inject resource 
	@PersistenceContext(unitName="MSD")
	private EntityManager em;
	
	private EntityRepository<User_story>user_storyRepository;
	// Constructor
	public Product_BacklogDataServiceEJB() {
	}
	// Init after constructor
	@PostConstruct
	public void init(){
		logger.info("POSTCONSTRUCT-INIT : " + this.em);
	}		
	@Override
	@GET 					/* MSD-S4/data/projects 		REST-resource: projects-collection*/
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Collection<Product_Backlog> toCollection() {
		logger.info("**** DEBUG REST toCollection()");
		return super.toCollection();
	}	
	
	
	@GET @Path("/{id}") 	/* MSD-S4/data/projects/data/{id} 	REST-resource: project-entity*/
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	public Product_Backlog getById(@PathParam("id") Integer id){ 
		Product_Backlog product_backlog = super.getById(id);
		logger.info("**** DEBUG REST getById(" + id +") = " +product_backlog);
		return product_backlog;
	}	
	
	@POST 					/* MSD-S4/data/projects 		REST-resource: projects-collection*/
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	public Collection<Product_Backlog> addIntoCollection(Product_Backlog product_backlog) {
		// save aggregate
		super.add(product_backlog);
		logger.info("**** DEBUG REST save aggregate POST");
		// return updated collection
		return super.toCollection();
	}
	
	@PUT @Path("/{id}") 	/* MSD-S4/data/projects/{id} 	REST-resource: project-entity*/	
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	@Override
	public Product_Backlog add(Product_Backlog product_backlog) {
		// restore aggregation-relation
		for (User_story u: product_backlog.getUser_stories())
			u.setProduct_backlog(product_backlog);
		logger.info("**** DEBUG REST restore aggregation-relation PUT");
		// save aggregate
		logger.info("**** DEBUG REST save aggregate PUT");
		product_backlog = super.add(product_backlog);
		// return updated collection	
		return product_backlog;
	}	
	
//	@Override
	@DELETE 				/* MSD-S4/data/projects 		REST-resource: projects-collection*/
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	public Collection<Product_Backlog> removeFromCollection(Product_Backlog product_backlog) {
		logger.info("DEBUG: called REMOVE - project: " + product_backlog);
		super.remove(product_backlog);
		return super.toCollection();
	}
	
	@DELETE @Path("/{id}") 	/* MSD-S4/data/projects/{id} 	REST-resource: project-entity*/	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	public void remove(@PathParam("id")Integer id) {
		logger.info("DEBUG: called REMOVE - ById() for projects >>>>>>>>>>>>>> simplified ! + id");
		Product_Backlog product_backlog = super.getById(id);
		super.remove(product_backlog);
	}
	
	// GET method on second repository for Release-type entities
	@GET @Path("/{projectid}/releases/{releaseid}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public User_story getUser_storyById(@PathParam("releaseid") Integer releaseid){
		logger.info("DEBUG: called getReleaseById() for projects >>>>>>>>>>>>>> simplified !");
		User_story user_story =user_storyRepository.getById(releaseid);
		return user_story;
	}
	
	/* Other test-proposal methods ************************************************************/
	@POST @Path("/new/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	// Aggregate factory method
	public Product_Backlog addProduct_backlog(@PathParam("id")Integer id){
		logger.info("**** DEBUG REST createNewProject POST");
		// create project aggregate
		Product_Backlog product_backlog = new Product_Backlog(id, "NEW Project" + "." + id);
		List<User_story> storyProduct_Backlog = new ArrayList<>();
		
		Integer storyCount = 3;
		for (int i=0; i<=storyCount-1; i++){
			storyProduct_Backlog.add(new User_story(null, "R: ",product_backlog.getCod() + "." + i));
		}
		product_backlog.setUser_stories(storyProduct_Backlog);		
		// save project aggregate
		this.add(product_backlog);
		// return project aggregate to service client
		return product_backlog;
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
		Product_Backlog dto = new Product_Backlog(1111, "Pro 1111");
		JAXBContext jaxbContext = JAXBContext.newInstance(Product_Backlog.class);
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
	public Product_Backlog addProduct_Backlog(Product_Backlog Product_BacklogToAdd) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String removeProduct_Backlog(Product_Backlog Product_BacklogToDelete) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Product_Backlog getProduct_BacklogByID(Integer cod) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Collection<Product_Backlog> getProduct_Backlogs() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Product_Backlog getProduct_BacklogByDenumire(String Detalii) {
		// TODO Auto-generated method stub
		return null;
	}
	
}


