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
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iid;
    @Column
    private String iname;
    @Column
    private String icategory;
    @Column
    private LocalDate idate;
    @Column
    private long iamount;

    @ManyToOne
    @JoinColumn(name = "Username")
    @JsonIgnore
    private User user;
}