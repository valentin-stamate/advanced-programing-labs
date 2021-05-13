package com.perosal.lab_11.message;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class MessageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

}
