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
import org.app.service.ejb.TaskDataService;
import org.app.service.ejb.TaskDataServiceEJB;
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
public class TestTaskDataServiceEJBArq {
	private static Logger logger = 
			Logger.getLogger(TestTaskDataServiceEJBArq.class.getName());
	
	@EJB // EJB DataService Ref
	private static TaskDataService service;
	
	@Deployment // Arquilian infrastructure
	public static Archive<?> createDeployment() {
	        return ShrinkWrap
	                .create(WebArchive.class, "msd-test.war")
	                .addPackage(Task.class.getPackage())
	                .addClass(TaskDataService.class)
	                .addClass(TaskDataServiceEJB.class)
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
	public void test3_AddTask() {
		logger.info("DEBUG: Junit TESTING: testAddFeature ...");
		
		Integer TaskToAdd = 3;
		for (int i=1; i <= TaskToAdd; i++){
			//service.addFeature(new Feature(100 + i, "Feature_" + (100 + i)));
			service.addTask(new Task(null, "Feature_","zero","Task"));
		}
		Collection<Task> taskuri = service.getTaskuri();
		assertTrue("Fail to add features!", taskuri.size() == TaskToAdd);
	}
	
	@Test
	public void test4_GetTaskuri() {
		logger.info("DEBUG: Junit TESTING: testGetTaskuri ...");
		
		Collection<Task> taskuri = service.getTaskuri();
		assertTrue("Fail to read taskuri!", taskuri.size()>0);
	}



	@Test
	public void test2_DeleteTask() {
		logger.info("DEBUG: Junit TESTING: testDeleteTask ...");
		
		Collection<Task> taskuri = service.getTaskuri();
		for (Task t: taskuri)
			service.removeTask(t);
		Collection<Task> TaskAfterDelete = service.getTaskuri();
		assertTrue("Fail to read features!", TaskAfterDelete.size() == 0);
	}	
}
/* http://localhost:8080/SCRUM-S2/data/features */