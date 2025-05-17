package national.exam.java.electricity_generating_prepaid_tokens;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ElectricityGeneratingPrepaidTokensApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectricityGeneratingPrepaidTokensApplication.class, args);
    }

}
