package HU4.HU4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Hu4Application {

	public static void main(String[] args) {
		SpringApplication.run(Hu4Application.class, args);
	}

}
