package com.perosal.lab_11.person;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.perosal.lab_11.auth.Encryption;
import com.perosal.lab_11.auth.JwtUtil;
import com.perosal.lab_11.request.APIError;
import com.perosal.lab_11.request.APISuccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/login")
    public ResponseEntity<Object> login(@RequestBody PersonModel personModel) {

        if (!personService.personExists(personModel)) {
            return new ResponseEntity<>(new APIError("User doesn't exist"), HttpStatus.BAD_REQUEST);
        }

        if (!personService.verifyPerson(personModel)) {
            return new ResponseEntity<>(new APIError("Invalid password"), HttpStatus.BAD_REQUEST);
        }

        personModel = personService.getByUsername(personModel.getUsername());

        return new ResponseEntity<>(JwtUtil.encode(personModel), HttpStatus.OK);
    }

    @GetMapping("persons")
    public ResponseEntity<Object> getPersons() {
        return new ResponseEntity<>(personService.getAllPersons(), HttpStatus.OK);
    }

    @PostMapping("person")
    public ResponseEntity<Object> addPerson(@RequestBody PersonModel personModel) {

        personModel.setPassword(Encryption.encrypt(personModel.getPassword()));

        if (personService.savePerson(personModel)) {
            return new ResponseEntity<>(new APISuccess("User inserted successfully"), HttpStatus.OK);
        }

        return new ResponseEntity<>(new APIError("User already exists"), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("person")
    public ResponseEntity<Object> modifyPerson(@RequestHeader(name = "Authorization") String token, @RequestBody PersonModel personModelJSON) {
        PersonModel personModel = getPersonModelFromToken(token);

        if (personModel == null) {
            return new ResponseEntity<>(new APIError("Invalid token"), HttpStatus.BAD_REQUEST);
        }

        personModel.setUsername(personModelJSON.getUsername());

        String newPassword = Encryption.encrypt(personModelJSON.getPassword());

        personModel.setPassword(newPassword);

        personService.updatePerson(personModel);

        return new ResponseEntity<>(new APISuccess("User updated"), HttpStatus.OK);
    }

    @DeleteMapping("person")
    public ResponseEntity<Object> deletePerson(@RequestHeader(name = "Authorization") String token, @RequestParam String username) {
        PersonModel personModel = getPersonModelFromToken(token);

        if (personModel == null) {
            return new ResponseEntity<>(new APIError("Invalid token"), HttpStatus.BAD_REQUEST);
        }

        if (!username.equals(personModel.getUsername())) {
            return new ResponseEntity<>(new APIError("You can't delete other users"), HttpStatus.NOT_ACCEPTABLE);
        }

        personService.deletePerson(personModel);

        return new ResponseEntity<>(new APISuccess("User deleted successfully"), HttpStatus.OK);
    }

    @GetMapping("person/friends")
    public ResponseEntity<Object> getFriends(@RequestHeader(name = "Authorization") String token) {
        PersonModel personModel = getPersonModelFromToken(token);

        if (personModel == null) {
            return new ResponseEntity<>(new APIError("Invalid token"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(personModel.getFriends(), HttpStatus.OK);
    }

    @PostMapping("person/friend")
    public ResponseEntity<Object> addFriend(@RequestHeader(name = "Authorization") String token, @RequestParam String username) {
        PersonModel personModel = getPersonModelFromToken(token);

        if (personModel == null) {
            return new ResponseEntity<>(new APIError("Invalid token"), HttpStatus.BAD_REQUEST);
        }

        if (username.equals(personModel.getUsername())) {
            return new ResponseEntity<>(new APIError("You can't add yourself"), HttpStatus.OK);
        }

        PersonModel friend = personService.getByUsername(username);

        if (friend == null) {
            return new ResponseEntity<>(new APIError("The user you want to add does't exist"), HttpStatus.NOT_ACCEPTABLE);
        }

        personService.addFriend(personModel, friend);

        return new ResponseEntity<>(new APISuccess("Friend added"), HttpStatus.OK);
    }

    private PersonModel getPersonModelFromToken(String token) {
        DecodedJWT decodedJWT = JwtUtil.decode(token);

        if (decodedJWT == null) {
            return null;
        }

        PersonModel personModel = new PersonModel();
        personModel.setObjectFromClaims(decodedJWT);

        personModel = personService.getByUsername(personModel.getUsername());

        return personModel;
    }

}
