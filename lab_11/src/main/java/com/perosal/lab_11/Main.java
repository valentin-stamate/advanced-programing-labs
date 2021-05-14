package com.perosal.lab_11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Main {

    /* You may find a typo: "persons" instead of "people"... yeah, I missed that */

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
