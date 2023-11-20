package com.learn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learn.Entities.Project;
@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>{
	List<Project> findByGen(String gen);
}
