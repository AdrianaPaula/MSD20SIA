package org.app.service.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.app.service.entities.Task;
import org.app.service.entities.User_story;



@Singleton
public class ProjectFactory{
//	@TransactionAttribute(TransactionAttributeType.SUPPORTS) // propagate transaction
//	public static Task buildProiect(Integer projectID, String name, Integer releaseCount){
//		
//		Task task = new Task(projectID, name + "." + projectID , new Date());
//		List<User_story> user_story = new ArrayList<>();
//		
//		Date dataPublicare = new Date();
//		Long interval =  30l; /*zile*/ //* 24 /*ore*/ * 60 /*min*/ * 60 /*sec*/ * 1000 /*milisec*/;
//		
//		for (int i=0; i<=releaseCount-1; i++){
//			user_story.add(new User_story(null, "R: " + task.getProjectNo() + "." + i, 
//					new Date(dataPublicare.getTime() + i * interval), task));
//		}
//		
//		p.setReleases(releasesProject);
//		
//		return project;
//	}
//
//	@Override
//	public String toString() {
//		return "ProjectFactory-instance";
//	}
}
