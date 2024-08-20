package com.pennywisenepal.financetracker.Controller;


import com.pennywisenepal.financetracker.Entity.User;
import com.pennywisenepal.financetracker.Service.UserService;
import jakarta.persistence.GeneratedValue;
import com.pennywisenepal.financetracker.Entity.Card;
import com.pennywisenepal.financetracker.Entity.Income;
import com.pennywisenepal.financetracker.Service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<Income> addBalance(@RequestBody Income income) {
        incomeService.addBalance(income);
        return new ResponseEntity<>(income, HttpStatus.OK);
    }

    @GetMapping("/totalbalance")
    public double getTotalBalamce() {
        return incomeService.getTotalBalance();
    }


    @GetMapping("/weekbalance")
    public List<Income> sortByWeek() {
        return incomeService.getLastWeekList();
    }

    @GetMapping("/monthbalance")

    public List<Income> sortByMonth() {
        return incomeService.getLastMonthList();

    }

    @GetMapping("/incomecard")

    public Card getIncomeProgress() {
        return incomeService.getIncomeDifference();
    }

    @GetMapping("/salarycard")
    public Card cardforSalary() {
        return incomeService.cardforSalary();

    }

    @GetMapping("/investmentcard")
    public Card cardforInvestment() {
        return incomeService.cardforInvestment();

    }

    @GetMapping("/otherscard")
    public Card cardForOthers() {
        return incomeService.cardforOthers();

    }

    @GetMapping("/realstatecard")
    public Card cardforRealState() {
        return incomeService.cardforRealstate();

    }

    @DeleteMapping("/delete/{iid}")
    public void deleteById(@PathVariable("iid") int iid) {
        incomeService.deleteIncome(iid);
    }

    @GetMapping("/current")
//
//    public String getCurrentUser() {
//
//        return incomeService.getCurrentUser().getUsername();
//    }
   public ResponseEntity<User> getCurrentUser() {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            return ResponseEntity.ok(currentUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}