package project.hongik_hospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@EnableJpaAuditing
@SpringBootApplication
public class HongikHospitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(HongikHospitalApplication.class, args);
	}
	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter();
	}
}
