package com.example.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetController {

	// http://localhost:8080/greet
	@GetMapping("/greet")
	public String greet() {
		return "have a great day";
	}

	// http://localhost:8080/sayHello/Nikhil
	@GetMapping("/sayHello/{username}")
	public String greet(@PathVariable("username") String name) {
		return "Welcome " + name;
	}

	// http://localhost:8080/sayHello/Nikhil/city/Haliyal
	@GetMapping("/sayHello/{username}/city/{city}")
	public String greet(@PathVariable("username") String name, @PathVariable("city") String city) {
		return "Welcome " + name + " your city is " + city;
	}

	// http://localhost:8080/show?name=Nik&salary=5000
	@GetMapping("/show")
	public String show(@RequestParam("name") String name, @RequestParam("salary") String salary) {
		//double sal = Double.parseDouble(salary) * 10;
		return "Welcome " + name + " your salary is " + salary;
	}

	// http://localhost:8080/showFruits
	@GetMapping("/showFruits")
	public List<String> showFruits() {
		return Arrays.asList("apple","kivi");
	}
}
