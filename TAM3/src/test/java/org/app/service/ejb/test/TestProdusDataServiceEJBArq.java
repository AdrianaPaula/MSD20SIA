package org.app.service.ejb.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;

import org.app.service.ejb.ProdusDataService;
import org.app.service.ejb.ProdusDataServiceEJB;

import org.app.service.entities.Produs;
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
public class TestProdusDataServiceEJBArq {
	private static Logger logger = 
			Logger.getLogger(TestProdusDataServiceEJBArq.class.getName());
	
	@EJB // EJB DataService Ref
	private static ProdusDataService service;
	
	@Deployment // Arquilian infrastructure
	public static Archive<?> createDeployment() {
	        return ShrinkWrap
	                .create(WebArchive.class, "msd-test.war")
	                .addPackage(Produs.class.getPackage())
	                .addClass(ProdusDataService.class)
	                .addClass(ProdusDataServiceEJB.class)
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
	public void test3_AddProdus() {
		logger.info("DEBUG: Junit TESTING: testAddFeature ...");
		
		Integer ProdusToAdd = 3;
		for (int i=1; i <= ProdusToAdd; i++){
			//service.addFeature(new Feature(100 + i, "Feature_" + (100 + i)));
			service.addProdus(new Produs(null, "Feature_","zero"));
		}
		Collection<Produs> produse = service.getProduse();
		assertTrue("Fail to add features!", produse.size() == ProdusToAdd);
	}
	
	@Test
	public void test4_GetProduse() {
		logger.info("DEBUG: Junit TESTING: testGetFeatures ...");
		
		Collection<Produs> produse = service.getProduse();
		assertTrue("Fail to read features!", produse.size()>0);
	}



	@Test
	public void test2_DeleteProdus() {
		logger.info("DEBUG: Junit TESTING: testDeleteFeature ...");
		
		Collection<Produs> produse = service.getProduse();
		for (Produs p: produse)
			service.removeProdus(p);
		Collection<Produs> ProdusAfterDelete = service.getProduse();
		assertTrue("Fail to read features!", ProdusAfterDelete.size() == 0);
	}	
}
/* http://localhost:8080/SCRUM-S2/data/features */