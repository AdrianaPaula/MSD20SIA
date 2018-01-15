package org.app.service.ejb.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Local;

import org.app.service.ejb.AngajatDataService;
import org.app.service.ejb.AngajatDataServiceEJB;
import org.app.service.ejb.ProdusDataService;
import org.app.service.ejb.ProdusDataServiceEJB;
import org.app.service.ejb.User_storyDataService;
import org.app.service.ejb.User_storyDataServiceEJB;
import org.app.service.entities.Angajat;
import org.app.service.entities.Produs;
import org.app.service.entities.Task;
import org.app.service.entities.User_story;
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
public class TestUser_storyDataServiceEJBArq {
	private static Logger logger = 
			Logger.getLogger(TestUser_storyDataServiceEJBArq.class.getName());
	
	@EJB // EJB DataService Ref
	private static User_storyDataService service;
	
	@Deployment // Arquilian infrastructure
	public static Archive<?> createDeployment() {
	        return ShrinkWrap
	                .create(WebArchive.class, "msd-test.war")
	                .addPackage(User_story.class.getPackage())
	                .addClass(User_storyDataService.class)
	                .addClass(User_storyDataServiceEJB.class)
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
	public void test3_AddUser_story() {
		logger.info("DEBUG: Junit TESTING: testAddFeature ...");
		
		Integer User_storyToAdd = 3;
		for (int i=1; i <= User_storyToAdd; i++){
			//service.addFeature(new Feature(100 + i, "Feature_" + (100 + i)));
			service.addUser_story(new User_story(null, "Marinescu Ioana","Judetul Iasi, Strada Castanilor"));
		}
		Collection<User_story> user_stories = service.getUser_stories();
		assertTrue("Fail to add features!", user_stories.size() == User_storyToAdd);
	}
	
	@Test
	public void test4_GetAnagajati() {
		logger.info("DEBUG: Junit TESTING: testGetFeatures ...");
		
		Collection<User_story> user_stories= service.getUser_stories();
		assertTrue("Fail to read features!", user_stories.size()>0);
	}



	@Test
	public void test2_DeleteAngajati() {
		logger.info("DEBUG: Junit TESTING: testDeleteFeature ...");
		
		Collection<User_story> user_stories = service.getUser_stories();
		for (User_story u: user_stories)
			service.removeUser_story(u);
		Collection<User_story> User_storyAfterDelete = service.getUser_stories();
		assertTrue("Fail to read features!", User_storyAfterDelete.size() == 0);
	}	
}
/* http://localhost:8080/SCRUM-S2/data/features */