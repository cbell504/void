package dev.christopherbell.thevoid;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"dev.christopherbell.thevoid", "dev.christopherbell.libs"})
@SpringBootApplication
public class VoidApp {

  public static void main(String[] args) {
    SpringApplication.run(VoidApp.class, args);
  }
}