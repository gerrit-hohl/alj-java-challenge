package jp.co.axa.apidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 * The API demo application.
 * </p>
 * <p>
 * Starts the application using an embedded H2 database. The application ca be
 * accessed using the following URLs:
 * <ul>
 * <li>Swagger UI : <a href=
 * "http://localhost:8080/swagger-ui.html">http://localhost:8080/swagger-ui.html</a></li>
 * <li>H2 UI : <a href=
 * "http://localhost:8080/h2-console">http://localhost:8080/h2-console</a><br/>
 * Don't forget to set the &quot;<code>JDBC URL</code>&quot; value as
 * &quot;<code>jdbc:h2:mem:testdb</code>&quot; for H2 UI.</li>
 * </ul>
 * </p>
 */
@EnableSwagger2
@SpringBootApplication
public class ApiDemoApplication {
	
	
	/**
	 * <p>
	 * Executes the API demo application.
	 * </p>
	 *
	 * @param args
	 *            The command line arguments.
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(ApiDemoApplication.class, args);
	}


	/**
	 * <p>
	 * Returns the documentation for the REST API.
	 * </p>
	 *
	 * @return The documentation.
	 */
	@Bean
	public Docket getDocket() {
		Docket docket;

		docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(this.getApiInfo());
		return docket;
	}
	
	
	/**
	 * <p>
	 * Returns the REST API information.
	 * </p>
	 *
	 * @return The REST API information.
	 */
	private ApiInfo getApiInfo() {
		ApiInfo apiInfo;


		apiInfo = new ApiInfoBuilder().title("Demo project for Spring Boot")
				.description("Demo project for Spring Boot challenge of AXA Japan")
				.contact(new Contact("Gerrit Hohl", "https://gerrit-hohl.users.sourceforge.net/", null)).version("0.0.1")
				.build();
		return apiInfo;
	}
	
	
}
