package com.perosal.lab_11.person;

import com.perosal.lab_11.auth.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<PersonModel> getAllPersons() {
        List<PersonModel> personModels = personRepository.findAll();

        for (PersonModel personModel : personModels) {
            personModel.setPassword("");
        }

        return personRepository.findAll();
    }

    public boolean savePerson(PersonModel personModel) {
        if (personExists(personModel)) {
            return false;
        }

        personRepository.save(personModel);
        return true;
    }

    public boolean updatePerson(PersonModel personModel) {
        personRepository.save(personModel);
        return true;
    }

    public boolean personExists(PersonModel personModel) {
        return personRepository.findByUsername(personModel.getUsername()) != null;
    }

    public boolean verifyPerson(PersonModel personModel) {
        PersonModel personDB = personRepository.findByUsername(personModel.getUsername());

        String password = personModel.getPassword();
        password = Encryption.encrypt(password);

        return password.equals(personDB.getPassword());
    }

    public PersonModel getByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    public void deletePerson(PersonModel personModel) {
        personRepository.delete(personModel);
    }

    public void addFriend(PersonModel personModel, PersonModel friend) {
        List<PersonModel> personModels = personModel.getFriends();

        for (PersonModel person : personModels) {
            if (person.getUsername().equals(friend.getUsername())) {
                return;
            }
        }

        personModel.addFriend(friend);
        personRepository.save(personModel);
    }

    public List<PersonModel> getMostConnected(int limit, boolean descending) {
        List<PersonModel> personModels = getAllPersons();

        personModels.sort(new PersonComparator());

        List<PersonModel> sortedPersonList = new ArrayList<>();

        if (descending) {
            for (int i = personModels.size() - 1; i >= 0 && limit > 0; i--, limit--) {
                sortedPersonList.add(personModels.get(i));
            }

            return sortedPersonList;
        }

        for (int i = 0; i < personModels.size() && limit > 0; i++, limit--) {
            sortedPersonList.add(personModels.get(i));
        }

        return sortedPersonList;
    }
}

class PersonComparator implements Comparator<PersonModel> {

    @Override
    public int compare(PersonModel personA, PersonModel personB) {
        return personB.getFriends().size() - personA.getFriends().size();
    }
}
