package com.revature.Wk1PairedProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.revature.Models")
@ComponentScan("com.revature")
@EnableJpaRepositories("com.revature.DAOs")
public class Wk1PairedProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(Wk1PairedProjectApplication.class, args);



	}

}
