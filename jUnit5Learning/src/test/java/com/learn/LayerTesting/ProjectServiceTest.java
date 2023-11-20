package com.learn.LayerTesting;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.learn.Entities.Project;
import com.learn.dao.ProjectRepository;
import com.learn.service.ProjectService;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
	@InjectMocks		// Inject the mock object to Project Service
	private ProjectService projectService;
	@Mock
	private ProjectRepository projectRepository;
	
	@Test
	@DisplayName("Should saave the movie object to database")
	void save() {
		Project project = new Project();
		//project.setId(1);
		project.setName("Traffice Controll");
		project.setGen("Android");
		project.setReleaseDate(LocalDate.of(2000, Month.APRIL, 24));
		
		when(projectRepository.save(any(Project.class))).thenReturn(project);
		
		//for Service
		Project newProject = projectService.save(project);
		
		assertNotNull(newProject);
		assertThat(newProject.getName()).isEqualTo("Traffice Controll");
		
	}
	
	@Test    
	@DisplayName("Should return movie size 2")
	void getProjects() {
		Project project = new Project();
		project.setName("Traffice Controll");
		project.setGen("Android");
		project.setReleaseDate(LocalDate.of(2000, Month.APRIL, 24));
		
		Project project2 = new Project();
		project2.setName("Rain Control");
		project2.setGen("Android & Web");
		project2.setReleaseDate(LocalDate.of(2024, Month.NOVEMBER, 19));
		
		List<Project> list = new ArrayList<Project>();
		list.add(project);
		list.add(project2);
		
		when(projectRepository.findAll()).thenReturn(list);
		
		List<Project> projects = projectService.getAllProjects();
		
		assertEquals(2, projects.size());
		assertNotNull(projects);
		
	}
	
	@Test
	@DisplayName("Should return prijhect object")
	void getProjectById() {
		Project project2 = new Project();
		project2.setId(1);
		project2.setName("Rain Control");
		project2.setGen("Android & Web");
		project2.setReleaseDate(LocalDate.of(2024, Month.NOVEMBER, 19));
		
		when(projectRepository.findById(anyInt())).thenReturn(Optional.of(project2));
		
		Project projectById = projectService.getProjectById(1);
		assertNotNull(projectById);
		assertThat(projectById.getId()).isEqualTo(1);
	}

	@Test
	@DisplayName("should throws the exception")
	void getProjectByIdForException() {
		Project project2 = new Project();
		project2.setId(1);
		project2.setName("Rain Control");
		project2.setGen("Android & Web");
		project2.setReleaseDate(LocalDate.of(2024, Month.NOVEMBER, 19));
		
		when(projectRepository.findById(1)).thenReturn(Optional.of(project2));
		
		assertThrows(RuntimeException.class, () -> {
			projectService.getProjectById(2);
		});
		
	}
	
	@Test
	@DisplayName("Should update teh project into the database")
	void updateProject() {
		Project project = new Project();
		project.setId(1);
		project.setName("Traffice Controll");
		project.setGen("Android");
		project.setReleaseDate(LocalDate.of(2000, Month.APRIL, 24));
		
		when(projectRepository.findById(anyInt())).thenReturn(Optional.of(project));
		when(projectRepository.save(any(Project.class))).thenReturn(project);
		project.setGen("Web");
		
		Project updatedProject = projectService.updateProject(1, project);
		
		assertNotNull(updatedProject);
		assertEquals("Web", updatedProject.getGen());
	}
	
	@Test
	@DisplayName("Should delete the movie from database")
	void deleteProject() {
		Project project = new Project();
		project.setId(1);
		project.setName("Traffice Controll");
		project.setGen("Android");
		project.setReleaseDate(LocalDate.of(2000, Month.APRIL, 24));
		
		when(projectRepository.findById(anyInt())).thenReturn(Optional.of(project));
		doNothing().when(projectRepository).delete(any(Project.class));
		
		projectService.deleteProject(1);
		verify(projectRepository, times(1)).delete(project);
	}
	
}
