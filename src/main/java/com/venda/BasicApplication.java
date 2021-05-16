package com.venda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class BasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicApplication.class, args);
	}
       
       @Bean
	public WebClient webClientClientes(WebClient.Builder builder) {
		return builder
			.baseUrl("http://www.mocky.io/v2/598b16291100004705515ec5")
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.build();
	}

	@Bean
	public WebClient webClientCompras(WebClient.Builder builder) {
		return builder
			.baseUrl("http://www.mocky.io/v2/598b16861100004905515ec7")
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.build();
	}
       
}
