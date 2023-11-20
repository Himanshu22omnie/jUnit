package com.learn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.InstanceOf;

import com.learn.Entities.Movies;

public class MoviesSpec {

	private Movies movies;

	@BeforeEach
	void init() {
		movies = new Movies();
	}

	@Test
	@DisplayName("List is empty when no moves are added")
	public void moviesEmptyWhenNoMoviesAdded() {
		// Arrange -> setup the data
		movies = new Movies();

		// Act -> call the method
		List<String> list = movies.getMovies();

		// Assert -> evaluate the condition
		// success scenario
		assertTrue(list.isEmpty(), () -> "Movies should be empty");

	}

	@Test
	void moviesList() {
		movies = new Movies();
		movies.add("Avatar 2");
		movies.add("XYZ");
		List<String> list = movies.list();
		assertEquals(2, list.size(), () -> "Movies list should have two Size");

	}

	@Test
	@DisplayName("shouldReturnMoviesAlphabetically")
	void moviesSorting() {
		movies.add("Spider man 6");
		movies.add("Terminator 5");
		movies.add("Mission Impossible 2");
		movies.add("Avater");

		List<String> arrangeListList = movies.arrange();
		assertEquals(Arrays.asList("Avater", "Mission Impossible 2", "Spider man 6", "Terminator 5"), arrangeListList,
				() -> "should return movies alphabetically");
	}

	@Test
	@DisplayName("should be empty when no movies passed to the add method")
	void movieShouldBeEmptyWhenAddIsCalledWithMovies() {
		movies.add(); // null is not empty
		List<String> list = movies.list();
		assertTrue(list.isEmpty(), () -> "Movies should be empty");
	}

	// first way
	@Test
	@DisplayName("1.should return an exception while adding any value in immutable list")
	void immutableList() {
		List<String> immutableList = List.of("Spider man", "Terminator", "Avater");
		
		assertThrows(UnsupportedOperationException.class, () -> {
			immutableList.add("new Movie");
		});
	}

	// Second way
	@Test
	@DisplayName("2.should return an exception while adding any value in immutable list")
	void testImmutableListModification() {
		List<String> mutableList = new ArrayList<>();
		mutableList.add("Spider man");
		mutableList.add("Terminator");
		mutableList.add("Avatar");

		List<String> immutableList = List.copyOf(mutableList);

		// Use assertThrows to verify that adding to the immutable list throws UnsupportableException
		assertThrows(UnsupportedOperationException.class, () -> {
			immutableList.add("New Movie"); // Trying to add will throw UnsupportedOperationException
		});

	}
	
	@Test
	@DisplayName("3.should return an exception while adding any value in immutable list")
	void movieReturnedShouldBeImmutable() {
		Movies movies = new Movies();
		movies.add("Terminator");
		
		List<String> list = movies.list();
		try {
			list.add("F & F");
			fail(() -> "Should not able to add new Movies");
		} catch (Exception e) {
			assertTrue(e instanceof UnsupportedOperationException, ()-> "Should throw this Exception");
		}
		
	}
}
