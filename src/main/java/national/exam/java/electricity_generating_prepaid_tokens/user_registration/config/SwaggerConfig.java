package national.exam.java.electricity_generating_prepaid_tokens.user_registration.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EUCL Electricity Generating Prepaid Tokens API")
                        .version("1.0")
                        .description("RESTful API for managing user authentication and password recovery"));
    }
}
