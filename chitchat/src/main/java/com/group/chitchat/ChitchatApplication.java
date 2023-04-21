package com.group.chitchat;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class ChitchatApplication {

  public static void main(String[] args) {
    SpringApplication.run(ChitchatApplication.class, args);
  }

}
