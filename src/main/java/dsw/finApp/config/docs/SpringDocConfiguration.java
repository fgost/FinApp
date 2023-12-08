package dsw.finApp.config.docs;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {
    @Bean
    public GroupedOpenApi publicApiV1() {
        return GroupedOpenApi.builder()
                .packagesToScan("dsw.finApp")
                .group("v1")
                .pathsToMatch("/**")
                .build();
    }
//    @Bean
//    public OpenAPI customOpenAPI(@Value("${spring.application.name}") String host) {
//        return new OpenAPI()
//                .addServersItem(new Server().url("/").description(host))
//                .components(new Components())
//                .info(new Info().title("FinApp").description(
//                                "Project responsible for providing a backend for the FinApp.")
//                        .termsOfService("http://swagger.io/terms/")
//                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
//    }
}
