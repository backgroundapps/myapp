package com.manollo.myapp;

import com.manollo.myapp.entity.Customer;
import com.manollo.myapp.repository.CustomerRepository;
import com.manollo.myapp.security.entity.Role;
import com.manollo.myapp.security.entity.User;
import com.manollo.myapp.security.repository.RoleRepository;
import com.manollo.myapp.security.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Application {
	
	private Role role = new Role();
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner inserRole(RoleRepository repository) {
		return (args) -> {
			log.info("\n === Insert Roles === \n");
			log.info("\n ==================== \n");
			role.setName("USER");
			repository.save(role);
			log.info("\n === role created === \n");
			log.info("\n ==================== \n");
			
		};
	}
	
	@Bean
	public CommandLineRunner inserUser(UserRepository repository) {
		return (args) -> {
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(); 
			User user = new User();
			user.setUsername("user");
			user.setPassword("password");
			
			
			log.info("\n === set pass    === /n");
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			log.info("\n =================== /n");

			java.util.Set<Role> roles = new java.util.HashSet<>();
			roles.add(role);
			log.info("\n === Role ID     === /n");
			log.info(" " + role.getId());
			log.info("\n =================== /n");

			user.setRoles(roles);

			log.info("\n === Insert User === /n");
			log.info("\n =================== /n");
			
			repository.save(user);
			log.info("\n === Created User === \n");
			log.info("\n ==================== \n");
			

		};
	}

	@Bean
	public CommandLineRunner inserCustomer(CustomerRepository repository) {
		return (args) -> {
			// save a couple of customers
			log.info(" --- > Saving Customer ...");
			repository.save(new Customer("Jack", "Bauer"));
			repository.save(new Customer("Chloe", "O'Brian"));
			repository.save(new Customer("Kim", "Bauer"));
			repository.save(new Customer("David", "Palmer"));
			repository.save(new Customer("Michelle", "Dessler"));

			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			
			for (Customer customer : repository.findAll()) {
				log.info(customer.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			repository.findById(1L)
				.ifPresent(customer -> {
					log.info("Customer found with findById(1L):");
					log.info("--------------------------------");
					log.info(customer.toString());
					log.info("");
				});

			// fetch customers by last name
			log.info("Customer found with findByLastName('Bauer'):");
			log.info("--------------------------------------------");
			repository.findByLastName("Bauer").forEach(bauer -> {
				log.info(bauer.toString());
			});
			// for (Customer bauer : repository.findByLastName("Bauer")) {
			// 	log.info(bauer.toString());
			// }
			log.info("");
		};
	}



}