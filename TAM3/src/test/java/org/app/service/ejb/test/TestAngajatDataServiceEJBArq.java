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
import org.app.service.ejb.ProdusDataService;
import org.app.service.ejb.ProdusDataServiceEJB;
import org.app.service.entities.Angajat;
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
public class TestAngajatDataServiceEJBArq {
	private static Logger logger = 
			Logger.getLogger(TestAngajatDataServiceEJBArq.class.getName());
	
	@EJB // EJB DataService Ref
	private static AngajatDataService service;
	
	@Deployment // Arquilian infrastructure
	public static Archive<?> createDeployment() {
	        return ShrinkWrap
	                .create(WebArchive.class, "msd-test.war")
	                .addPackage(Angajat.class.getPackage())
	                .addClass(AngajatDataService.class)
	                .addClass(AngajatDataServiceEJB.class)
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
	public void test3_AddAngajat() {
		logger.info("DEBUG: Junit TESTING: testAddFeature ...");
		
		Integer AngajatToAdd = 3;
		for (int i=1; i <= AngajatToAdd; i++){
			//service.addFeature(new Feature(100 + i, "Feature_" + (100 + i)));
			service.addAngajat(new Angajat(null, "Marinescu Ioana","Judetul Iasi, Strada Castanilor"));
		}
		Collection<Angajat> angajati = service.getAngajati();
		assertTrue("Fail to add features!", angajati.size() == AngajatToAdd);
	}
	
	@Test
	public void test4_GetAnagajati() {
		logger.info("DEBUG: Junit TESTING: testGetFeatures ...");
		
		Collection<Angajat> angajati= service.getAngajati();
		assertTrue("Fail to read features!", angajati.size()>0);
	}



	@Test
	public void test2_DeleteAngajati() {
		logger.info("DEBUG: Junit TESTING: testDeleteFeature ...");
		
		Collection<Angajat> angajati = service.getAngajati();
		for (Angajat a: angajati)
			service.removeAngajat(a);
		Collection<Angajat> AngajatAfterDelete = service.getAngajati();
		assertTrue("Fail to read features!", AngajatAfterDelete.size() == 0);
	}	
}
/* http://localhost:8080/SCRUM-S2/data/features */