package com.perosal.lab_11.legacy.database.models;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.perosal.lab_11.auth.Mappable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Person implements Serializable, Mappable {

    public static final String TABLE_NAME = "persons";
    public static final String ID_COLUMN_NAME = "id";
    public static final String USERNAME_COLUMN_NAME = "username";
    public static final String PASSWORD_COLUMN_NAME = "password";

    private long id;
    private String username;
    private String password;

    public Person(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Person(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Person() { }

    public long getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
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

        Person person = (Person) o;

        return username.equals(person.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
