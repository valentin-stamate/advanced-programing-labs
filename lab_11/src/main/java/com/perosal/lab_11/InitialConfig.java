package com.perosal.lab_11;

import com.perosal.lab_11.person.PersonModel;
import com.perosal.lab_11.person.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitialConfig {

    @Bean
    CommandLineRunner commandLineRunner(PersonRepository personRepository) {
        return args -> {
            PersonModel userA = new PersonModel("ValentinSt", "123456789");
            PersonModel userB = new PersonModel("Andrei", "987654321");
            PersonModel userC = new PersonModel("Ana", "123456789");

            personRepository.save(userA);
            personRepository.save(userB);
            personRepository.save(userC);

            userA.addFriend(userB);
            userB.addFriend(userC);

            personRepository.save(userA);
            personRepository.save(userB);
        };
    }

}
