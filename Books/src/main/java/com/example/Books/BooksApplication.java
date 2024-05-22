package com.example.Books;

import com.example.Books.role.Role;
import com.example.Books.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing (auditorAwareRef = "auditorAware")
                    //for lastModifiedDate & CreatedDate (BaseEntity)
                   // code  repeated in multiple places
                  // automatically tracking and recording changes
		//When an entity is created or updated,
		// JPA Auditing will call the getCurrentAuditor (ApplicationAuditAware)
		// method to get the current user's ID and set it in the appropriate fields.
@EnableAsync
public class BooksApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(RoleRepository roleRepository ) {
		return args -> {
			if (roleRepository.findByName("USER").isEmpty()) {
				roleRepository.save(Role.builder().name("USER").build());
			}
		};
	}

}
