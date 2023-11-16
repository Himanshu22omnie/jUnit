package com.test.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.entity.Employee;
import java.util.List;
import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	 Optional<Employee> findByEmail(String email);
}
