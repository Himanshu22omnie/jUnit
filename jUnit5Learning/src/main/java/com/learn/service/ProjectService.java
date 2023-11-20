package com.learn.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.learn.Entities.Project;
import com.learn.dao.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {
	private final ProjectRepository projectRepository;
	
	public Project save(Project project) {
		return projectRepository.save(project);
	}
	
	
	public List<Project> getAllProjects(){
		return projectRepository.findAll();
	}
	
	
	public Project getProjectById(Integer id) {
		return projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project is not Exist"));
	}
	
	
	public Project updateProject(Integer id, Project project)  
	{
		Project existingProject = projectRepository.findById(id).get();
		existingProject.setGen(project.getGen()); // 'project' jo data postman se aata hai use 'existingProject' m set kr rahe hai.
		existingProject.setName(project.getName());
		existingProject.setReleaseDate(project.getReleaseDate());
		return projectRepository.save(existingProject);
	}
	
	
	public void deleteProject(Integer id) {
		Project existingProject = projectRepository.findById(id).get();
		projectRepository.delete(existingProject);
	}
}
