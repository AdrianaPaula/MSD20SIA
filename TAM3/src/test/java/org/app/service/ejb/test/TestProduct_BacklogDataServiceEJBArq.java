package org.app.service.ejb.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;

import org.app.service.ejb.AngajatDataService;
import org.app.service.ejb.AngajatDataServiceEJB;
import org.app.service.ejb.Product_BacklogDataService;
import org.app.service.ejb.Product_BacklogDataServiceEJB;
import org.app.service.ejb.ProdusDataService;
import org.app.service.ejb.ProdusDataServiceEJB;
import org.app.service.entities.Angajat;
import org.app.service.entities.Product_Backlog;
import org.app.service.entities.Produs;
import org.app.service.entities.Task;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(Arquillian.class) 
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestProduct_BacklogDataServiceEJBArq {
	private static Logger logger = 
			Logger.getLogger(TestProduct_BacklogDataServiceEJBArq.class.getName());
	
	@EJB // EJB DataService Ref
	private static Product_BacklogDataService service;
	
	@Deployment // Arquilian infrastructure
	public static Archive<?> createDeployment() {
	        return ShrinkWrap
	                .create(WebArchive.class, "msd-test.war")
	                .addPackage(Product_Backlog.class.getPackage())
	                .addClass(Product_BacklogDataService.class)
	                .addClass(Product_BacklogDataServiceEJB.class)
	                .addAsResource("META-INF/persistence.xml")
	                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	
	@Test
	public void test1_GetMessage() {
		logger.info("DEBUG: Junit TESTING: getMessage ...");
		String response = service.getMessage();
		assertNotNull("Data Service failed!", response);
		logger.info("DEBUG: EJB Response ..." + response);
	}
	
	@Test
	public void test3_AddProduct_Backlog() {
		logger.info("DEBUG: Junit TESTING: testAddFeature ...");
		
		Integer Product_BacklogToAdd = 3;
		for (int i=1; i <= Product_BacklogToAdd; i++){
			//service.addFeature(new Feature(100 + i, "Feature_" + (100 + i)));
			service.addProduct_Backlog(new Product_Backlog(null, "Dureaza mai mult decat un an de zile "));
		}
		Collection<Product_Backlog> product_backlogs = service.getProduct_Backlogs();
		assertTrue("Fail to add features!", product_backlogs.size() == Product_BacklogToAdd);
	}
	
	@Test
	public void test4_GetProduct_Backlogs() {
		logger.info("DEBUG: Junit TESTING: testGetFeatures ...");
		
		Collection<Product_Backlog> product_backlogs= service.getProduct_Backlogs();
		assertTrue("Fail to read features!", product_backlogs.size()>0);
	}



	@Test
	public void test2_DeleteProduct_Backlogs() {
		logger.info("DEBUG: Junit TESTING: testDeleteFeature ...");
		
		Collection<Product_Backlog> product_backlogs = service.getProduct_Backlogs();
		for (Product_Backlog p: product_backlogs)
			service.removeProduct_Backlog(p);
		Collection<Product_Backlog> Product_BacklogAfterDelete = service.getProduct_Backlogs();
		assertTrue("Fail to read features!", Product_BacklogAfterDelete.size() == 0);
	}	
}
/* http://localhost:8080/SCRUM-S2/data/features */