package com.perosal.lab_11.message;

import com.perosal.lab_11.person.PersonModel;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class MessageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private PersonModel personModel;
    private String message;

    public MessageModel(long id, PersonModel personModel, String message) {
        this.id = id;
        this.personModel = personModel;
        this.message = message;
    }

    public MessageModel(PersonModel personModel, String message) {
        this.personModel = personModel;
        this.message = message;
    }

    public MessageModel() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PersonModel getPersonModel() {
        return personModel;
    }

    public void setPersonModel(PersonModel personModel) {
        this.personModel = personModel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
