package com.perosal.lab_11;

import com.perosal.lab_11.auth.Encryption;
import com.perosal.lab_11.message.MessageController;
import com.perosal.lab_11.message.MessageModel;
import com.perosal.lab_11.message.MessageService;
import com.perosal.lab_11.person.PersonModel;
import com.perosal.lab_11.person.PersonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitialConfig {

    @Bean
    CommandLineRunner commandLineRunner(PersonService personService, MessageService messageService) {
        return args -> {
            PersonModel userA = new PersonModel("ValentinSt", Encryption.encrypt("123456789"));
            PersonModel userB = new PersonModel("Andrei", Encryption.encrypt("987654321"));
            PersonModel userC = new PersonModel("Vlad", Encryption.encrypt("123456789"));

            personService.savePerson(userA);
            personService.savePerson(userB);
            personService.savePerson(userC);

            MessageModel messageModel = new MessageModel(userB, "Hello world!");
            messageService.saveMessage(messageModel);

            userA.addMessage(messageModel);
            personService.savePerson(userA);

            personService.addFriend(userA, userB);
            personService.addFriend(userB, userC);

            personService.savePerson(userA);
            personService.savePerson(userB);
        };
    }

}
