package com.pennywisenepal.financetracker.Entity;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OtpVerify {

    private int otp;
    private String username;
}