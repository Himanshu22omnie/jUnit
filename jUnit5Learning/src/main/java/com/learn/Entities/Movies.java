package com.learn.Entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Movies {
	private List<String> movies = new ArrayList<>();
	public List<String> getMovies() {
		return Collections.emptyList();
	}

	public void add(String movie) {
		movies.add(movie);
	}

	public List<String> list() {
		//return movies;
		return Collections.unmodifiableList(movies);  // create unmodifiableList
	}

	public List<String> arrange() {
		movies.sort(Comparator.naturalOrder());
		return movies;
	}

	public void add() {
		// TODO Auto-generated method stub
		
	}
}
