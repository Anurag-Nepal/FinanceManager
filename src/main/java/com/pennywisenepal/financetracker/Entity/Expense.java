package com.pennywisenepal.financetracker.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eid;
    @Column
    private String ename;
    @Column
    private String ecategory;
    @Column
    private LocalDate edate;
    @Column
    private long eamount;
    @ManyToOne
    @JoinColumn(name = "username")
    @JsonIgnore
    private User user;
}