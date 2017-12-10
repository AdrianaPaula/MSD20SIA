package org.app.service.ejb;

import java.util.Collection;
import java.util.List;

import javax.ejb.Remote;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.app.patterns.EntityRepository;
import org.app.service.entities.EntityBase;

import org.app.service.entities.Produs;
import org.app.service.entities.Task;


@Remote
public interface TaskDataService{
	// CREATE or UPDATE
	Task addTask(Task TaskToAdd);

	// DELETE
	String removeTask(Task TaskToDelete);

	// READ
	Task getTaskByID(Integer id_task);
	Collection<Task> getTaskuri();
	
	// Custom READ: custom query
	Task getTaskByNume_Task(String nume_task);
	
	// Others
	String getMessage();
}

