package org.app.service.ejb;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import org.app.patterns.EntityRepository;
import org.app.patterns.EntityRepositoryBase;
import org.app.service.entities.Task;
import org.app.service.entities.User_story;

/*
@Stateless @LocalBean
public class TaskUser_storyDataServiceEJB extends EntityRepositoryBase<Task>
		implements TaskUser_storyDataService, Serializable {
	private static Logger logger = Logger.getLogger(TaskUser_storyDataServiceEJB.class.getName());

	@EJB
	private User_storyDataService user_storyService; 	
	
	private EntityRepository<User_story> user_storyRepository;
	
	//@EJB
	//private FeatureDataService featureDataService; 

	@Inject 
	private ProjectFactory projectFactory;
	
	@PostConstruct
	public void init() {
		user_storyRepository = new EntityRepositoryBase<User_story>(this.em,User_story.class);
		
		//logger.info("POSTCONSTRUCT-INIT sprintDataService: " + this.sprintDataService);
		logger.info("POSTCONSTRUCT-INIT releaseRepository: " + this.user_storyRepository);
		//logger.info("POSTCONSTRUCT-INIT featureDataService: " + this.featureDataService);
		logger.info("POSTCONSTRUCT-INIT projectFactory: " + this.projectFactory);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	@Interceptors({ValidatorInterceptor.class})
	public Task createNewTask(Integer id_task){
		// chain transaction execution on Singleton ProjectFactory
		Task task = ProjectFactory.buildProiect(id_task, "NEW Project", 3);
		this.add(task);
		return task;
	}
		
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED) // out-of-transaction
	public User_story getUser_storyById(Integer id_userstory) {
		return user_storyRepository.getById(id_userstory);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED) // out-of-transaction
	public String getMessage() {
		return "ProjectSprint DataService is working...";
	}
} */