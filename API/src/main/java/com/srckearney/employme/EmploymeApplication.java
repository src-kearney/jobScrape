package com.srckearney.employme;

import com.srckearney.employme.Model.JobPage;
import com.srckearney.employme.Model.JobPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.lang.*;

@SpringBootApplication
public class EmploymeApplication {

	@Autowired
	private JobPageRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(EmploymeApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			repository.save(new JobPage("TitleTest", "CompanyTest", "PageTest","DescriptionTest","queryTest", "locationTest"));
		};
	}

}