package com.pennywisenepal.financetracker.Controller;



import jakarta.mail.MessagingException;
import com.pennywisenepal.financetracker.Entity.OtpVerify;
import com.pennywisenepal.financetracker.Entity.User;
import com.pennywisenepal.financetracker.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public String addnewUser(@RequestBody User user) throws MessagingException {
        return  userService.register(user);

    }

    @PostMapping("/verify")
    public String Verify(@RequestBody OtpVerify otpVerify)
    {
        return userService.registerVerification(otpVerify);

    }

    @PostMapping("/login")

    public String login(@RequestBody User user)
    {
        return userService.Login(user);



    }


    @GetMapping("/otp")
    public int generateOtp()
    {
        return  userService.generateOtp();
    }
}