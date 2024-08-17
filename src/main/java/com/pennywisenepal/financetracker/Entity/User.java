package com.pennywisenepal.financetracker.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String username;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private Boolean isVerified;
    @Column
    private Integer otp;



}