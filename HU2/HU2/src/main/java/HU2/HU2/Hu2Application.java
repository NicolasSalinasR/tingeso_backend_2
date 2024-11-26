package HU2.HU2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Hu2Application {

	public static void main(String[] args) {
		SpringApplication.run(Hu2Application.class, args);
	}

}
