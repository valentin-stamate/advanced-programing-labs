package com.perosal.lab_11.person;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.perosal.lab_11.auth.Mappable;
import com.perosal.lab_11.message.MessageModel;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "persons")
public class PersonModel implements Mappable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String username;
    @Column
    private String password;

    @ManyToMany(cascade = CascadeType.REMOVE)
    private final List<PersonModel> friends = new ArrayList<>();

    @OneToMany
    private final List<MessageModel> messages = new ArrayList<>();

    public PersonModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public PersonModel(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public PersonModel() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PersonModel> getFriends() {
        return friends;
    }

    public List<MessageModel> getMessages() {
        return messages;
    }

    public void addMessage(MessageModel messageModel) {
        messages.add(messageModel);
    }

    public boolean addFriend(PersonModel personModel) {
        for (PersonModel person : friends) {
            if (person.username.equals(personModel.getUsername())) {
                return false;
            }
        }

        friends.add(personModel);
        return true;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("username", username);

        return map;
    }

    @Override
    public void setObjectFromClaims(DecodedJWT decodedJWT) {
        if (decodedJWT == null) {
            return;
        }
        id = decodedJWT.getClaim("id").asLong();
        username = decodedJWT.getClaim("username").asString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonModel that = (PersonModel) o;

        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }
}
