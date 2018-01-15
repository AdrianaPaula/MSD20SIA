package org.app.service.ejb;
import javax.ejb.Remote;
import org.app.patterns.EntityRepository;

import org.app.service.entities.Task;
import org.app.service.entities.User_story;

@Remote
public interface TaskUser_storyDataService extends EntityRepository<Task> {
	Task createNewTask(Integer id_task);
	User_story getUser_storyById(Integer id_userstory);
	String getMessage();
	

}
