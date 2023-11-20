package com.learn;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.learn.Entities.Project;
import com.learn.dao.ProjectRepository;

import net.bytebuddy.implementation.bytecode.ByteCodeAppender.Size;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;;

@DataJpaTest
public class ProjectRepositoryTest {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	private Project project2;
	private Project project3;
	
	@BeforeEach
	void init() {
		project2 = new Project();
		project2.setName("Project 1");
		project2.setGen("Web");
		project2.setReleaseDate(LocalDate.of(2000, Month.APRIL, 22));
		
		project3 = new Project();
		project3.setName("Project 3");
		project3.setGen("Android");
		project3.setReleaseDate(LocalDate.of(2000, Month.APRIL, 19));
		
	}
	
	
	@Test
	void save() {
		//Arrange
		
		//Act
		Project prt = projectRepository.save(project2);
		//Assert
		
		assertNotNull(prt); 
		//or
		assertThat(prt.getId()).isNotNull();
	}
	
	@Test
	@DisplayName("It Should return all the data")
	void getAll() {
		//Arrange
		
		projectRepository.save(project3);
		
		projectRepository.save(project2);
		
		Project p3 = new Project();
		p3.setName("Project 3");
		p3.setGen("Backend");
		p3.setReleaseDate(LocalDate.of(2000, Month.APRIL, 19));
		projectRepository.save(p3);
		//Act
		List<Project> prtProject = projectRepository.findAll();
		//Assert
		assertNotNull(prtProject);
		assertThat(prtProject).isNotNull(); //More fluent Way
		assertEquals(3, prtProject.size());
		
	}
	
	@Test
	@DisplayName("get Project on the basis of Id")
	void getProjectById() {
	
		projectRepository.save(project2);
		
		projectRepository.save(project3);
		
		projectRepository.findById(project3.getId());
		
		assertThat(project3).isNotNull();
		assertThat(project3.getName().equals("Project 2"));
		assertEquals("Project 3", project3.getName());
		assertThat(project3.getReleaseDate().isBefore(LocalDate.of(2000, Month.APRIL, 24)));
	}
	
	@Test
	@DisplayName("Update Your Data")
	void updateProject() {
		projectRepository.save(project2);
		
		Project existingData = projectRepository.findById(project2.getId()).get();
		 existingData.setName("New Project");
		 existingData.setGen("Nw Gen");
		 existingData.setReleaseDate(LocalDate.of(2000, Month.APRIL, 27)); // kya release date es date se pehle hai.
		 projectRepository.save(existingData);
		 
		assertEquals(LocalDate.of(2000, Month.APRIL, 27), existingData.getReleaseDate()); 
	}
	
	@Test
	@DisplayName("It should delete existing data")
	void deletingMovie() {
		Project p4 = new Project();
		p4.setName("Project 4");
		p4.setGen("Backend");
		p4.setReleaseDate(LocalDate.of(2000, Month.APRIL, 19));
		projectRepository.save(p4);
		
		projectRepository.delete(p4);
		Optional<Project> existingProject = projectRepository.findById(p4.getId());
		List<Project> pj = projectRepository.findAll();
		
		assertEquals(0, pj.size());
		assertThat(existingProject).isEmpty();
	}
	
	// Test Case For Custom Method
	@Test
	@DisplayName("it should return Project By Gen")
	void fidProjectByGen() {
		
		projectRepository.save(project3);
		
		List<Project> findByGen = projectRepository.findByGen(project3.getGen());
		
		assertNotNull(findByGen);
	}
	
}
