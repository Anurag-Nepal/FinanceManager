package com.pennywisenepal.financetracker.Controller;
import lombok.Data;
import com.pennywisenepal.financetracker.Entity.Card;
import com.pennywisenepal.financetracker.Entity.Expense;
import com.pennywisenepal.financetracker.Entity.ExpenseAdder;
import com.pennywisenepal.financetracker.Entity.User;
import com.pennywisenepal.financetracker.Service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;


    @PostMapping("/add")
//Adding a New Expense to the Database
    public ResponseEntity<Expense> addExpense(@RequestBody ExpenseAdder expenseAdder)
    {
        expenseService.addExpense(expenseAdder);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    //Total Expenses in Month Sum Value

    @GetMapping("/totalexpense")

    public double getTotalExpense()
    {
        return expenseService.getTotalExpense();
    }

    @GetMapping("/weekexpense")
    public List<Expense> sortByWeek()
    {
        return expenseService.getLastWeekList();

    }

    //List to be Fetched at Expenses SubCategory
    @GetMapping("/monthexpense")

    public List<Expense> sortByMonth()
    {
        return expenseService.getLastMonthList();

    }
//Expense Card to be Shown At Dashboard

    @GetMapping("/expensecard")

    public Card getExpenseProgress()
    {
        return expenseService.getExpenseDifference();

    }

    //Card For Groceries Category
    @GetMapping("/groceriescard")
    public Card cardForGrocery()
    {
        return expenseService.CardForGroceries();  }


    //Card For Bills Category
    @GetMapping("/billscard")
    public Card cardForbills()
    {
        return expenseService.cardForBills();
    }


    //Card For Entertaintment Category
    @GetMapping("/entertaintmentcard")
    public Card cardForEntertaintment()
    {
        return expenseService.cardForEntertaintment();

    }

    //Card For Others Expense Category
    @GetMapping("/otherscard")
    public Card cardForOthers()
    {
        return expenseService.cardForOthers();

    }
    @DeleteMapping("/delete/{eid}")
    public String deleteById(@PathVariable("eid") int eid)
    {
        expenseService.deleteExpense(eid);
        return "Deleted Successfully";
    }



}