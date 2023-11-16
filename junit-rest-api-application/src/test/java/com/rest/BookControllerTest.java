package com.rest;

import static org.assertj.core.api.Assertions.offset;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.naming.java.javaURLContextFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.Matchers.hasSize;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rest.controller.BookController;
import com.rest.dao.BookRepository;
import com.rest.entity.Book;


@SpringBootTest
@RunWith(SpringRunner.class)
//@ExtendWith(MockitoExtension.class)
public class BookControllerTest { 
	
	
	private MockMvc mockMvc;
	
	//It's the main class provided by the Jackson library for reading and writing JSON or other formats. [It allows you to convert Java objects to their JSON representation (serialization) and vice versa (deserialization)]
	ObjectMapper objectMapper = new ObjectMapper();
	
	// This is an instance obtained from ObjectMapper and is responsible for writing (or serializing) Java objects as JSON.
	// It provides additional configuration options specifically related to the writing process, like setting indentation, specifying filters, or configuring pretty printing. 
	// In testing, it's useful when you need more control over the serialization process or when you want to customize how objects are written to JSON.
	ObjectWriter objectWriter = objectMapper.writer();
	
	@Mock
	private BookRepository bookRepository;
	
	
	//BookController is annotated with @InjectMocks, indicating that the mocked [BookRepository (created with @Mock) should be injected into the BookController].
	@InjectMocks
	private BookController bookController;
	
	Book r1 = new Book(1L,"Atomic Habits","How to build better habits",5);
	Book r2 = new Book(2L,"Thinking fast and slow","How to create good mental model about thinking",5);
	Book r3 = new Book(3L,"Grokking Algorithms","Learn algorithms the fun way",5);
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this); // This line initializes any mock objects annotated with @Mock within the test class.
		//Here, MockMvc is an API provided by Spring for testing Spring MVC applications. This line creates a MockMvc instance that is set up with a standalone configuration using MockMvcBuilders. 
		//It initializes the bookController for testing purposes. This allows you to simulate HTTP requests and test the behavior of your controller without actually deploying your application in a server environment.
		this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
	}
	
	@Test
	public void getAllRecords_sucess() throws Exception
	{
		List<Book> records=new ArrayList<>(Arrays.asList(r1,r2,r3));
		Mockito.when(bookRepository.findAll()).thenReturn(records);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/book")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(3)))
				.andExpect(jsonPath("$[2].name", is("Grokking Algorithms")))
				.andExpect(jsonPath("$[1].name", is("Thinking fast and slow")));
				
	}
	
	@Test
	public void getBookById_success() throws Exception {
		Mockito.when(bookRepository.findById(r1.getBookId())).thenReturn(java.util.Optional.of(r1));
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/book/1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.name", is("Atomic Habits")));
	}
	
	@Test
	public void createRecord_success() throws Exception{
		Book record = Book.builder()
				.bookId(4l)
				.name("Let's C")
				.summry("Understaing of C Language")
				.rating(5)
				.build();
		
		Mockito.when(bookRepository.save(record)).thenReturn(record);
		String content = objectWriter.writeValueAsString(record);
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/book/saveBook")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(content);
		
		mockMvc.perform(mockRequest)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.name", is("Let's C")));
	}
	
	@Test
	public void updateRecord_success() throws Exception {
		Book updatedRecord = Book.builder()
				.bookId(1l)
				.name("Let's C Programming")
				.summry("This is Best for C lang basic concept")
				.rating(4)
				.build();
		
		Mockito.when(bookRepository.findById(r1.getBookId())).thenReturn(Optional.of(r1));
//		if (r1.getBookId() == null) {
//			throw new Exception("Book Not found at this id");
//		}
			Mockito.when(bookRepository.save(updatedRecord)).thenReturn(updatedRecord);
		
			String updatedContent = objectWriter.writeValueAsString(updatedRecord);
			MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/book")
			  	.contentType(MediaType.APPLICATION_JSON)
			  	.accept(MediaType.APPLICATION_JSON)
			  	.content(updatedContent);
		mockMvc.perform(mockRequest)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.name", is("Let's C Programming")));
	}
	
	@Test
	public void deleteBookbyId_success() throws Exception{
		Mockito.when(bookRepository.findById(r2.getBookId())).thenReturn(Optional.of(r2));
		
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/book/bookId/1",r1.getBookId())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

	
	
}
