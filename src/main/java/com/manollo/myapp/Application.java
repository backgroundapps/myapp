package com.manollo.myapp;

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
}