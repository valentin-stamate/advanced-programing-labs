package com.perosal.lab_11;

import com.perosal.lab_11.auth.JwtUtil;
import com.perosal.lab_11.person.PersonModel;
import org.junit.jupiter.api.Test;

class MainTests {

    @Test
    void contextLoads() {
        PersonModel personModel = new PersonModel(1, "Valentin", "123456789");

        String token = JwtUtil.encode(personModel);

        PersonModel personModelDecoded = new PersonModel();

        personModelDecoded.setObjectFromClaims(JwtUtil.decode(token));

        if (personModel.getId() == personModelDecoded.getId()) {
            System.out.println("Equals");
        } else {
            System.out.println("Not equals");
        }
    }

}
