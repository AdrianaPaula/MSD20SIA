package org.app.service.ejb;

import org.app.patterns.EntityRepository;

import org.app.service.entities.Task;
import org.app.service.entities.User_story;

public interface TaskUser_storyDataService extends EntityRepository<Task> {
	Task createNewTask(Integer id_task);
	User_story getUser_storyById(Integer id_userstory);
	String getMessage();
	

}
