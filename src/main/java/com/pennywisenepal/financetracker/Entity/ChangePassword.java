package com.pennywisenepal.financetracker.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePassword {
    private String newPassword;
    private String email;
    private Integer otp;

}
