package payservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestApiPaymentServiceApplication {
    @Bean
    public Gson gsonCreate() {
        return new GsonBuilder().create();
    }

    public static void main(String[] args) {
        SpringApplication.run(RestApiPaymentServiceApplication.class, args);
    }
}