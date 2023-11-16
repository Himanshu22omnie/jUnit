package com.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.config.entity.Person;

@SpringBootTest
class JUnitDemoApplicationTests {
	private Calculator calculator = new Calculator();
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void testSum() {
		int expectedResult = 30;
		int actualResult = calculator.Add(10, 10, 10);
		
		assertThat(actualResult).isEqualTo(expectedResult);
		
	}
	
	@Test
	@Disabled // for disable the test case
	void testMulti() {
		int expectedResult = 60;
		int actualResult = calculator.Multi(10, 2, 3);
		
		assertThat(actualResult).isEqualTo(expectedResult);
		
	}
	
	@Test
	void testCompare() {
		boolean actualResult = calculator.compare(8, 8);
		assertThat(actualResult).isTrue();
	}
	
}
