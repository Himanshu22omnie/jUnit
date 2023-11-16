package com.rest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "book_record")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long bookId;
	private String name;
	private String summry;
	private int rating;
}
