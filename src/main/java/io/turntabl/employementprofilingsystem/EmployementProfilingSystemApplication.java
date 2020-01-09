package io.turntabl.employementprofilingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@SpringBootApplication
public class EmployementProfilingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployementProfilingSystemApplication.class, args);
	}

}
