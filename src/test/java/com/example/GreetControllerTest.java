package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.controllers.GreetController;

@SpringBootTest
public class GreetControllerTest {

	@Test
	public void greetTest() {
		GreetController greetController = new GreetController();
		String actual = greetController.greet("Nikhil");
		String expected = "Welcome Nikhil";
		assertEquals(expected, actual);
	}
}
