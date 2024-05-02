package com.example.alwaysSpring.model;

import jakarta.persistence.*;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String Writer;

    @Column
    String postContent;
}
