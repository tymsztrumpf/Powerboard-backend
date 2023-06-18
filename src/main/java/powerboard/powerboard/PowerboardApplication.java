package powerboard.powerboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class PowerboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(PowerboardApplication.class, args);
	}

}
