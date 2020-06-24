package com.sn.ex.zuulproxy.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "appuser")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    @Id
    @Column(name="username", nullable=false, unique = true, length = 50)
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="email", nullable=false, unique = true, length=50)
    private String email;
    @Column(name="firstName", nullable=false, length=50)
    private String firstName;
    @Column(name="lastName", nullable=false, length=50)
    private String lastName;
    @Column(name="mobilePhone", nullable=true, length=10)
    private String mobilePhone;
    @Column(name = "isAdmin")
    private Boolean isAdmin = false;
    @Column(name = "isDeleted")
    private Boolean isDeleted = false;

}
