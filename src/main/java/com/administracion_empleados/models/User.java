package com.administracion_empleados.models;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "serial")
    private long id;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "PROFILE_PHOTO_URL")
    private String profilePhotoUrl;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "OCCUPATION")
    private String occupation;
    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;
    @Column(name = "AGE")
    private int age;
    @Column(name = "GENDER")
    private String gender;
    @Column(name = "ROLE")
    private String role;
    @Column(name = "HIRING_DATE")
    private Date hiringDate;

    

    public User() {
    }

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    
}
