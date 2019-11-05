package br.ufmg.dcc.saracura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SaraCuraApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaraCuraApplication.class, args);
    }

}
