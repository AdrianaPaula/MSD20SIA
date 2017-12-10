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
import org.app.service.entities.Angajat;
import org.app.service.entities.EntityBase;

import org.app.service.entities.Produs;
import org.app.service.entities.User_story;


@Remote
public interface User_storyDataService{
	// CREATE or UPDATE
	User_story addUser_story(User_story User_storyToAdd);

	// DELETE
	String removeUser_story(User_story User_storyToDelete);

	// READ
	User_story getUser_storyByID(Integer id_userstory);
	Collection<User_story> getUser_stories();
	
	// Custom READ: custom query
	User_story getUser_storyByNume(String nume);
	
	// Others
	String getMessage();
}

