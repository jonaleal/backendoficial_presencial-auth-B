package co.udea.airline.api.utils.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

    @Value("${airline-api.openapi.dev-url}")
    private String devUrl;

    @Value("${airline-api.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("airline@udea.edu.co");
        contact.setName("AIRLINE");
        contact.setUrl("https://www.airline.com");

        License mitLicense = new License().name("MIT License").url("https://airline.com/licenses/mit/");

        Info info = new Info()
                .title("AIRLINE API")
                .version("1.0")
                .contact(contact)
                .description("Esta API expone endpoints para AIRLINE.").termsOfService("https://www.airline.com/terms")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
