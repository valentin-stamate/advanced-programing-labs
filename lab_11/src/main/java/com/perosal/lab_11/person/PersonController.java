package com.perosal.lab_11.person;

import com.perosal.lab_11.util.Time;
import com.perosal.lab_11.util.Util;
import com.perosal.lab_11.auth.Encryption;
import com.perosal.lab_11.auth.JwtUtil;
import com.perosal.lab_11.legacy.database.dao.FriendShipDao;
import com.perosal.lab_11.legacy.database.dao.PersonDao;
import com.perosal.lab_11.legacy.database.models.Friendship;
import com.perosal.lab_11.legacy.database.models.Person;
import com.perosal.lab_11.request.ResponseSuccess;
import com.perosal.lab_11.request.ResponseError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            return new ResponseEntity<>(new ResponseSuccess("User doesn't exist"), HttpStatus.BAD_REQUEST);
        }

        if (!personService.verifyPerson(personModel)) {
            return new ResponseEntity<>(new ResponseSuccess("Invalid password"), HttpStatus.BAD_REQUEST);
        }

        personModel = personService.getByUsername(personModel.getUsername());

        return new ResponseEntity<>(JwtUtil.encode(personModel), HttpStatus.OK);
    }

    @GetMapping("/people")
    public ResponseEntity<Object> getPeople() {

        List<PersonModel> people = personService.getAllPeople();

        for (PersonModel personModel : people) {
            personModel.getMessages().clear();
            personModel.getFriends().clear();
            personModel.setPassword("");
        }

        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    @PostMapping("/person")
    public ResponseEntity<Object> addPerson(@RequestBody PersonModel personModel) {

        personModel.setPassword(Encryption.encrypt(personModel.getPassword()));

        if (personService.savePerson(personModel)) {
            return new ResponseEntity<>(new ResponseError("User inserted successfully"), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseSuccess("User already exists"), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/person")
    public ResponseEntity<Object> modifyPerson(@RequestHeader(name = "Authorization") String token, @RequestBody PersonModel personModelJSON) {
        PersonModel personModel = personService.getPersonModelFromToken(token);

        if (personModel == null) {
            return new ResponseEntity<>(new ResponseSuccess("Invalid token"), HttpStatus.BAD_REQUEST);
        }

        personModel.setUsername(personModelJSON.getUsername());

        String newPassword = Encryption.encrypt(personModelJSON.getPassword());

        personModel.setPassword(newPassword);

        personService.updatePerson(personModel);

        return new ResponseEntity<>(new ResponseError("User updated"), HttpStatus.OK);
    }

    @DeleteMapping("/person")
    public ResponseEntity<Object> deletePerson(@RequestHeader(name = "Authorization") String token) {
        PersonModel personModel = personService.getPersonModelFromToken(token);

        if (personModel == null) {
            return new ResponseEntity<>(new ResponseSuccess("Invalid token"), HttpStatus.BAD_REQUEST);
        }

        personService.deletePerson(personModel);

        return new ResponseEntity<>(new ResponseError("User deleted successfully"), HttpStatus.OK);
    }

    @GetMapping("/person/friends")
    public ResponseEntity<Object> getFriends(@RequestHeader(name = "Authorization") String token) {
        PersonModel personModel = personService.getPersonModelFromToken(token);

        if (personModel == null) {
            return new ResponseEntity<>(new ResponseSuccess("Invalid token"), HttpStatus.BAD_REQUEST);
        }

        List<PersonModel> friends = personModel.getFriends();

        for (PersonModel friend : friends) {
            friend.getFriends().clear();
            friend.setPassword("");
        }

        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @PostMapping("/person/friend")
    public ResponseEntity<Object> addFriend(@RequestHeader(name = "Authorization") String token, @RequestParam String username) {
        PersonModel personModel = personService.getPersonModelFromToken(token);

        if (personModel == null) {
            return new ResponseEntity<>(new ResponseSuccess("Invalid token"), HttpStatus.BAD_REQUEST);
        }

        if (username.equals(personModel.getUsername())) {
            return new ResponseEntity<>(new ResponseSuccess("You can't add yourself"), HttpStatus.OK);
        }

        PersonModel friend = personService.getByUsername(username);

        if (friend == null) {
            return new ResponseEntity<>(new ResponseSuccess("The user you want to add doesn't exist"), HttpStatus.NOT_ACCEPTABLE);
        }

        personService.addFriend(personModel, friend);

        return new ResponseEntity<>(new ResponseError("Friend added"), HttpStatus.OK);
    }

    @GetMapping("/person/top_connected")
    public ResponseEntity<Object> getTopConnected(@RequestParam("limit") int limit) {
        return new ResponseEntity<>(personService.getMostConnected(Math.abs(limit), limit < 0), HttpStatus.OK);
    }

    @GetMapping("/important_users")
    public ResponseEntity<Object> getImportantPeople() {
        PersonDao personDao = new PersonDao();
        List<Person> people = personDao.getAll();

        /* CREATION OF THE GRAPH */
        Map<Person, Integer> personToVertex = new HashMap<>();

        for (int i = 0; i < people.size(); i++) {
            personToVertex.put(people.get(i), i);
        }

        final int n = people.size();

        List<Integer>[] Graph = new List[n];

        for (int i = 0; i < n; i++) {
            Graph[i] = new ArrayList<>();

            Person person = people.get(i);
            List<Person> friends = personDao.getFriends(person);

            for (Person friend : friends) {
                int j = personToVertex.get(friend);

                Graph[i].add(j);
            }

        }

        List<Person> importantPeople = new ArrayList<>();

        /* THE ALGORITHM */
        /* INSPIRED FROM https://www.geeksforgeeks.org/articulation-points-or-cut-vertices-in-a-graph/ */

        boolean[] visited = new boolean[n];
        int[] discovered = new int[n];
        int[] low = new int[n];
        int[] parent = new int[n];
        boolean[] artPoints = new boolean[n];

        for (int i = 0; i < n; i++) {
            parent[i] = -1;
        }

        Time time = new Time();

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                Util.dfsUtil(i, Graph, visited, discovered, low, parent, artPoints, time);
            }
        }

        for (int i = 0; i < n; i++) {
            if (artPoints[i]) {
                importantPeople.add(people.get(i));
            }
        }

        for (Person person : importantPeople) {
            person.setPassword("");
        }

        return new ResponseEntity<>(importantPeople, HttpStatus.OK);
    }

    /* LEGACY ENDPOINTS */

    @GetMapping("/legacy/people")
    public ResponseEntity<Object> legacyGetPeople() {
        PersonDao personDao = new PersonDao();

        List<Person> people = personDao.getAll();

        for (Person person : people) {
            person.setPassword("");
        }

        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    @GetMapping("/legacy/login")
    public ResponseEntity<Object> legacyLogin(@RequestBody Person person) {
        PersonDao personDao = new PersonDao();

        if (personDao.findByUsername(person.getUsername()) == null) {
            return new ResponseEntity<>(new ResponseSuccess("User doesn't exist"), HttpStatus.BAD_REQUEST);
        }

        PersonModel personModel = new PersonModel(person.getUsername(), person.getPassword());

        if (!personService.verifyPerson(personModel)) {
            return new ResponseEntity<>(new ResponseSuccess("Invalid password"), HttpStatus.BAD_REQUEST);
        }

        person = personDao.findByUsername(person.getUsername());

        return new ResponseEntity<>(JwtUtil.encode(personModel), HttpStatus.OK);
    }

    @PostMapping("/legacy/person")
    public ResponseEntity<Object> legacyAddPerson(@RequestBody Person person) {
        PersonDao personDao = new PersonDao();

        person.setPassword(Encryption.encrypt(person.getPassword()));

        if (personDao.save(person)) {
            return new ResponseEntity<>(new ResponseError("User inserted successfully"), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseSuccess("User already exists"), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/legacy/person")
    public ResponseEntity<Object> legacyModifyPerson(@RequestHeader(name = "Authorization") String token, @RequestBody Person personModelJSON) {
        PersonDao personDao = new PersonDao();
        PersonModel personModel = personService.getPersonModelFromToken(token);

        if (personModel == null) {
            return new ResponseEntity<>(new ResponseSuccess("Invalid token"), HttpStatus.BAD_REQUEST);
        }

        Person person = new Person(personModel.getId(), personModel.getUsername(), personModel.getPassword());

        person.setUsername(personModelJSON.getUsername());

        String newPassword = Encryption.encrypt(personModelJSON.getPassword());
        personModel.setPassword(newPassword);

        personDao.updatePerson(person, person.getId());

        return new ResponseEntity<>(new ResponseError("User updated"), HttpStatus.OK);
    }

    @DeleteMapping("/legacy/person")
    public ResponseEntity<Object> legacyDeletePerson(@RequestHeader(name = "Authorization") String token) {
        PersonDao personDao = new PersonDao();
        PersonModel personModel = personService.getPersonModelFromToken(token);

        if (personModel == null) {
            return new ResponseEntity<>(new ResponseSuccess("Invalid token"), HttpStatus.BAD_REQUEST);
        }

        Person person = new Person(personModel.getId(), personModel.getUsername(), personModel.getPassword());

        personDao.delete(person);

        return new ResponseEntity<>(new ResponseError("User deleted successfully"), HttpStatus.OK);
    }

    @GetMapping("/legacy/person/friends")
    public ResponseEntity<Object> legacyGetFriends(@RequestHeader(name = "Authorization") String token) {
        PersonDao personDao = new PersonDao();
        PersonModel personModel = personService.getPersonModelFromToken(token);

        if (personModel == null) {
            return new ResponseEntity<>(new ResponseSuccess("Invalid token"), HttpStatus.BAD_REQUEST);
        }

        Person person = new Person(personModel.getId(), personModel.getUsername(), personModel.getPassword());

        return new ResponseEntity<>(personDao.getFriends(person), HttpStatus.OK);
    }

    @PostMapping("/legacy/person/friend")
    public ResponseEntity<Object> legacyAddFriend(@RequestHeader(name = "Authorization") String token, @RequestParam String username) {
        PersonDao personDao = new PersonDao();
        FriendShipDao friendShipDao = new FriendShipDao();

        PersonModel personModel = personService.getPersonModelFromToken(token);

        if (personModel == null) {
            return new ResponseEntity<>(new ResponseSuccess("Invalid token"), HttpStatus.BAD_REQUEST);
        }

        if (username.equals(personModel.getUsername())) {
            return new ResponseEntity<>(new ResponseSuccess("You can't add yourself"), HttpStatus.OK);
        }

        Person person = personDao.findByUsername(personModel.getUsername());
        Person friend = personDao.findByUsername(username);

        if (friend == null) {
            return new ResponseEntity<>(new ResponseSuccess("The user you want to add doesn't exist"), HttpStatus.NOT_ACCEPTABLE);
        }

        Friendship friendship = new Friendship(person.getId(), friend.getId());
        friendShipDao.save(friendship);

        return new ResponseEntity<>(new ResponseError("Friend added"), HttpStatus.OK);
    }

}


