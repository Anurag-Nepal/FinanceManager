package com.pennywisenepal.financetracker.Controller;



import com.pennywisenepal.financetracker.Entity.*;

import com.pennywisenepal.financetracker.Repository.UserRepository;
import com.pennywisenepal.financetracker.Service.EmailService;
import com.pennywisenepal.financetracker.Service.ExpenseService;
import com.pennywisenepal.financetracker.Service.IncomeService;
import com.pennywisenepal.financetracker.Service.UserService;
import jakarta.mail.AuthenticationFailedException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController

public class HomepageListDisplayer {

    @Autowired
    private final ExpenseService expenseService;
    @Autowired
    private final IncomeService incomeService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public HomepageListDisplayer(ExpenseService expenseService, IncomeService incomeService) {
        this.expenseService = expenseService;
        this.incomeService = incomeService;
    }


    @GetMapping("/random")
    public List<Object> random1() {
        List<Expense> expenses = new ArrayList<>(expenseService.getLastWeekList());
        List<Income> incomes = new ArrayList<>(incomeService.getLastWeekList());

        List<SortableWrapper> wrappers = new ArrayList<>();
        for (Expense expense : expenses) {
            wrappers.add(new SortableWrapper(expense.getEdate(), expense));
        }
        for (Income income : incomes) {
            wrappers.add(new SortableWrapper(income.getIdate(), income));
        }

        // Sort by date
        wrappers.sort(Comparator.comparing(SortableWrapper::getDate).reversed());

        // Extract sorted items
        List<Object> sortedItems = wrappers.stream()
                .map(SortableWrapper::getItem)
                .collect(Collectors.toList());

        return sortedItems;
    }


    @GetMapping("/savingcard")
    public Card getSavings() {
        Card card = new Card();
        double lastMonthIncome = incomeService.getLastMonth();
        double thisMonthIncome = incomeService.getThisMonth();

        double lastMonthExpense = expenseService.getLastMonth();
        double thisMonthExpense = expenseService.getThisMonth();

        double lastMonthSavings = Math.max(0, lastMonthIncome - lastMonthExpense);
        double thisMonthSavings = Math.max(0, thisMonthIncome - thisMonthExpense);

        double total = thisMonthSavings;
        double difference = thisMonthSavings - lastMonthSavings;
        double percentage = 0;

        if (lastMonthSavings != 0) {
            percentage = (difference / lastMonthSavings) * 100;
        }

        card.setPercentage(Math.abs(percentage));
        card.setTotal(Math.abs(total));

        if (thisMonthSavings < lastMonthSavings) {
            card.setMessage("Decreased by " + Math.abs(difference));
        } else if (thisMonthSavings > lastMonthSavings) {
            card.setMessage("Increased By " + difference);
        } else {
            card.setMessage("No change");
        }

        return card;
    }


    private static class SortableWrapper {
        private LocalDate date;
        private Object item;

        public SortableWrapper(LocalDate date, Object item) {
            this.date = date;
            this.item = item;
        }

        public LocalDate getDate() {
            return date;
        }

        public Object getItem() {
            return item;
        }


    }

    @GetMapping("/user")
    public Profile currentuser() {
        User user = userRepository.findByUsername(userService.getcurrentusername());
        Profile profile = new Profile();
        String name = user.getUsername().substring(0, 8);
        profile.setUsername("@" + name.toLowerCase());
        profile.setName(name);
        return profile;
    }
    @PostMapping("/forgot")
public String sendOtp(@RequestBody ChangePassword password) throws MessagingException {
    User user = userRepository.findByEmail(password.getEmail());
        if(!user.getIsVerified())
        {
            throw new MessagingException("User Associated With this email could not be found");
        }
        String to = password.getEmail();
        Integer otp = user.getOtp();
    String sub = "Forgot Password Mail";
    String body = "We received a request to reset your password. To proceed with the reset, please use the OTP code provided below:\n" +
            "\n" +
            "Your OTP Code: \n" + otp +
            "\n" +
            "This code is valid for the next 10 minutes. If you did not request a password reset, please disregard this email." +
            " Your account is safe and secure.\n" +
            "\n" +
            "To reset your password, enter the OTP on the password reset page. If you encounter any issues or need further assistance," +
            " please contact our support team at pennywisenepal@gmail.com." +
            "Best Regards \n" +
            "PennyWiseNepal";
    emailService.sendEmail(to, sub, body);
return "Otp Sent Successfully";

}
@PostMapping("/otp")
    public String validateOtp(@RequestBody ChangePassword password) throws Exception {
        User user = userRepository.findByEmail(password.getEmail());
        if(password.getOtp().equals(user.getOtp()))
        {
            return "Otp Verified Successfully";
        }
        else
            throw new Exception("Otp Was Incorrect");

    }



@PostMapping("/changepass")
    public String changePassword(@RequestBody ChangePassword password)
{
    User user =  userRepository.findByEmail(password.getEmail());
    user.setPassword(passwordEncoder.encode(password.getNewPassword()));
     userRepository.save(user);
     return "The Password was Successfully Changed";

}



}


