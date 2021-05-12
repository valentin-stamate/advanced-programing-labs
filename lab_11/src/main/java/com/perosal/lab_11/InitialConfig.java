package com.perosal.lab_11;

import com.perosal.lab_11.person.PersonModel;
import com.perosal.lab_11.person.PersonRepository;
import com.perosal.lab_11.person.PersonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitialConfig {

    @Bean
    CommandLineRunner commandLineRunner(PersonService personService) {
        return args -> {
            PersonModel userA = new PersonModel("ValentinSt", "123456789");
            PersonModel userB = new PersonModel("Andrei", "987654321");
            PersonModel userC = new PersonModel("Ana", "123456789");

            personService.savePerson(userA);
            personService.savePerson(userB);
            personService.savePerson(userC);

            personService.addFriend(userA, userB);
            personService.addFriend(userB, userC);

            personService.savePerson(userA);
            personService.savePerson(userB);
        };
    }

}
