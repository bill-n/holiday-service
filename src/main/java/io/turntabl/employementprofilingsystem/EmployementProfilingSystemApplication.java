package io.turntabl.employementprofilingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.io.IOException;
import java.security.GeneralSecurityException;

@ComponentScan(basePackages = "io.turntabl.*")
@EnableSwagger2
@EnableAutoConfiguration
@SpringBootApplication
public class EmployementProfilingSystemApplication {

	public static void main(String[] args) throws GeneralSecurityException, IOException {

		SpringApplication.run(EmployementProfilingSystemApplication.class, args);
	}

}
