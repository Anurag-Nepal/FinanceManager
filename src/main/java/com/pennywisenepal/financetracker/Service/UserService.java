package com.pennywisenepal.financetracker.Service;




import jakarta.mail.MessagingException;
import com.pennywisenepal.financetracker.Entity.OtpVerify;
import com.pennywisenepal.financetracker.Entity.User;
import com.pennywisenepal.financetracker.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.SecureRandom;


@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private EmailService emailService;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            // Assuming you have a method to convert UserDetails to your User entity
            return userRepository.findByUsername(userDetails.getUsername());
        }
        return null; // Or throw an exception if preferred
    }


    public String register(@RequestBody User user) throws MessagingException {
        if (user == null || user.getEmail() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("User information is incomplete");
        }
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsVerified(false);
        int otp = generateOtp();
        user.setOtp(otp);
        String to = user.getEmail();
        String body = "Hey, Welcome to PennyWise.\n" +
                "This mail has been sent to you for verification to register on our site. " +
                "If you haven't registered, kindly ignore this mail.\n" +
                "The OTP for your registration is " + otp + ".\n" +
                "Team PennyWise Nepal" +
                "Kindly Keep your Otp Safe and Dont Share With Anyone";

        String sub = "OTP Verification Mail";
        emailService.sendEmail(to, sub, body);
        userRepository.save(user);
        return "Needs Verification";
    }


    public String registerVerification(@RequestBody OtpVerify otpVerify) {

        User user = userRepository.findByUsername(otpVerify.getUsername());
        if (user.getIsVerified())
        {
            return "User Already Exists And Is Already Verified";
        }
        if (user.getOtp().equals(otpVerify.getOtp())) {
            user.setIsVerified(true);
            userRepository.save(user);
            return "Registered SuccessFully Proceed To Login";
        }
        else {

            userRepository.deleteById(user.getUsername());
            return "Inorrect Otp Entered";
        }


    }


    public String Login(User user) {

        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser == null || !existingUser.getIsVerified()) {
            return "User not found  Cant Proceed Further ";
        }
        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated())
            return jwtService.generateToken(user.getUsername());
        return "Authentication Failed";
    }

    public Integer generateOtp() {
        SecureRandom secureRandom = new SecureRandom();
        int randomInt = 100000 + secureRandom.nextInt(900000); // Random integer between 100,000 and 999,999
        return randomInt;

    }


}

