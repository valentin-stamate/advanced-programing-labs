package com.perosal.lab_11.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<PersonModel, Long> {

    @Query("SELECT pm FROM PersonModel pm WHERE pm.username = ?1")
    PersonModel findByUsername(String username);

}
