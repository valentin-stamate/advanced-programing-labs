package com.perosal.lab_11.message;

import com.perosal.lab_11.person.PersonModel;
import com.perosal.lab_11.person.PersonService;
import com.perosal.lab_11.request.ResponseSuccess;
import com.perosal.lab_11.request.ResponseError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {

    private final MessageService messageService;
    private final PersonService personService;

    @Autowired
    public MessageController(MessageService messageService, PersonService personService) {
        this.messageService = messageService;
        this.personService = personService;
    }

    @GetMapping("messages")
    public ResponseEntity<Object> getMessages(@RequestHeader(name = "Authorization") String token) {
        PersonModel personModel = personService.getPersonModelFromToken(token);

        if (personModel == null) {
            return new ResponseEntity<>(new ResponseSuccess("Invalid token"), HttpStatus.BAD_REQUEST);
        }

        List<MessageModel> messages = personModel.getMessages();

        for (MessageModel messageModel : messages) {
            messageModel.getPersonModel().getMessages().clear();
            messageModel.getPersonModel().getFriends().clear();
            messageModel.getPersonModel().setPassword("");
        }

        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @PostMapping("message")
    public ResponseEntity<Object> addMessage(@RequestHeader(name = "Authorization") String token, @RequestBody MessageModel messageModel) {
        PersonModel personModel = personService.getPersonModelFromToken(token);

        if (personModel == null) {
            return new ResponseEntity<>(new ResponseSuccess("Invalid token"), HttpStatus.BAD_REQUEST);
        }

        PersonModel personModelTo = personService.getByUsername(messageModel.getPersonModel().getUsername());

        if (personModelTo == null) {
            return new ResponseEntity<>(new ResponseSuccess("User you want to sent the message to doesn't exist"), HttpStatus.BAD_REQUEST);
        }

        personService.savePerson(personModelTo);

        MessageModel message = new MessageModel(personModel, messageModel.getMessage());
        messageService.saveMessage(message);

        personModelTo.addMessage(message);

        personService.savePerson(personModelTo);

        System.out.printf("Message |%-20s| was sent from |%-15s| -> |%-15s|", messageModel.getMessage(), personModel.getUsername(), personModelTo.getUsername());

        return new ResponseEntity<>(new ResponseError("The message was sent"), HttpStatus.OK);
    }

}
