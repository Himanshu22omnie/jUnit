package com.learn.LayerTesting;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.Entities.Project;
import com.learn.controller.ProjectController;
import com.learn.service.ProjectService;

@SpringBootTest(classes = ProjectControllerTest.class)
//@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {
	
	@Mock
	private ProjectService projectService;
	
	@Autowired
	private MockMvc mockMvc;
	
//	@Autowired
	private ObjectMapper objectMapper=new ObjectMapper();
	
	private Project project2;
	private Project project3;
	
	@BeforeEach
	void init() {
		project2 = new Project();
		project2.setName("Project 1");
		project2.setGen("Web");
		//project2.setReleaseDate(LocalDate.of(2000, Month.APRIL, 22));
		
		project3 = new Project();
		project3.setName("Project 3");
		project3.setGen("Android");
		//project3.setReleaseDate(LocalDate.of(2000, Month.APRIL, 19));
		
	}
	
	@Test
	void shouldCreateNewProject() throws Exception {
//		doReturn(project2).when(projectService.save(project2));
		when(projectService.save(any(Project.class))).thenReturn(project2);
		
		this.mockMvc.perform(post("/projects")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(project2)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", is(project2.getName())))
				.andExpect(jsonPath("$.gen", is(project2.getGen())));
				//.andExpect(jsonPath("$.relaseDate", is(project2.getReleaseDate().toString())));
	}
	
}
