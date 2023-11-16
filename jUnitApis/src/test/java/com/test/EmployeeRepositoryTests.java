package com.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.test.Dao.EmployeeRepository;
import com.test.entity.Employee;

//@DataJpaTest
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeRepositoryTests {

	@Autowired
	private EmployeeRepository employeeRepository;

//	@Test
//	@Order(5)
//	public void saveEmployee() {
//		Employee employee = Employee.builder()
//							.firstName("Smriti")
//							.lastName("Trevedi")
//							.email("smriti@gmail.com")
//							.build();
//		employeeRepository.save(employee);
//		
//		
//		assertThat(employee.getId()).isGreaterThan(0);
//	}
	
	@Test
	@Order(1)
	public void getEmployee() {
		Employee employee = employeeRepository.findById(1l).get();
		
		assertThat(employee.getId()).isEqualTo(1);
	}
	
	@Test
	@Order(2)
	public void getListOfEmployees() {
		List<Employee> employees = employeeRepository.findAll();
		assertThat(employees.size()).isGreaterThan(2);
	}
	
	@Test
	@Order(3)
	@Rollback(value = false)
	public void updateEmployeeTest() {
		Employee employee = employeeRepository.findById(1L).get();
		employee.setFirstName("Rupak");
		employee.setLastName("Chauhan");
		employee.setEmail("rupak@gmail.com");
		
		Employee employeeUpdated = employeeRepository.save(employee);
		
		assertThat(employeeUpdated.getEmail()).isEqualTo("rupak@gmail.com");
	}
	
	@Test
	@Order(4)
//	@Rollback(value = false)
	public void deleteEmployee() {
		Employee employee = employeeRepository.findById(3l).get();
		employeeRepository.delete(employee);
		
		//for test employee is deleted or not 
		Employee emp = null;
		Optional<Employee> optionalEmployee = employeeRepository.findByEmail("hjf@gmail.com");
		
		if(optionalEmployee.isPresent()) {
			emp = optionalEmployee.get();
		}
		assertThat(emp).isNull();
	}
}
