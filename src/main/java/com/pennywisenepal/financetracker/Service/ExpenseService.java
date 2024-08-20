package com.pennywisenepal.financetracker.Service;


import com.pennywisenepal.financetracker.Entity.*;
import com.pennywisenepal.financetracker.Repository.ExpenseRepository;
import com.pennywisenepal.financetracker.Repository.IncomeRepository;
import com.pennywisenepal.financetracker.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ExpenseService {


    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private UserService userService;

    private double  expense;



    public ResponseEntity<Expense> addExpense(ExpenseAdder expenseAdder)

    {
        User user =userService. getCurrentUser();
        Expense expense = new Expense();
        expense.setUser(user);
        expense.setEdate(LocalDate.now());
        expense.setEcategory(expenseAdder.getEcategory());
        expense.setEname(expenseAdder.getEname());
        expense.setEamount(expenseAdder.getEamount());
        expenseRepository.save(expense);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public double getTotalExpense()
    {

        User user = userService. getCurrentUser();
        expense=expenseRepository.getExpenseLastThirtyDaysForUser(user,LocalDate.now().minusDays(30));
        return expense;
    }


    public List<Expense> getLastMonthList()
    {
        User user = userService. getCurrentUser();
        LocalDate startDate = LocalDate.now().minus(30, ChronoUnit.DAYS);
        LocalDate endDate = LocalDate.now().minus(1, ChronoUnit.DAYS);
        List<Expense> expenses = expenseRepository.findByUserAndEdateBetweenOrderByEdateDesc(user,startDate, endDate);
        return expenses;

    }

    public List<Expense> getLastWeekList()
    {
        User user = userService. getCurrentUser();
        LocalDate startDate = LocalDate.now().minus(7, ChronoUnit.DAYS);
        LocalDate endDate = LocalDate.now().minus(1, ChronoUnit.DAYS);
        List<Expense> expenses = expenseRepository.findByUserAndEdateBetweenOrderByEdateDesc(user,startDate, endDate);
        return expenses;

    }




    public double getThisMonth() {
        LocalDate startDate = LocalDate.now().minus(30, ChronoUnit.DAYS);
        LocalDate endDate = LocalDate.now();
        User user =userService. getCurrentUser();
        List<Expense> expenses = expenseRepository.findByUserAndEdateBetweenOrderByEdateDesc(user,startDate, endDate);
        return expenses.stream().mapToDouble(Expense::getEamount).sum();
    }

    public double getLastMonth() {
        LocalDate startDate = LocalDate.now().minus(60, ChronoUnit.DAYS);
        LocalDate endDate = LocalDate.now().minus(31, ChronoUnit.DAYS);
        User user = userService. getCurrentUser();
        List<Expense> expenses = expenseRepository.findByUserAndEdateBetweenOrderByEdateDesc(user,startDate, endDate);
        return expenses.stream().mapToDouble(Expense::getEamount).sum();
    }


    public double getGroceriesThisMonth() {
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();
        User user =userService. getCurrentUser();
        List<Expense> grocery = expenseRepository.findByUserAndEcategoryAndEdateBetween(user,"Groceries",startDate,endDate);
        return grocery.stream().mapToDouble(Expense::getEamount).sum();
    }

    public double getGroceriesLastMonth() {
        LocalDate startDate = LocalDate.now().minusDays(60);
        LocalDate endDate = LocalDate.now().minusDays(31);
        User user = userService. getCurrentUser();
        List<Expense> grocery = expenseRepository.findByUserAndEcategoryAndEdateBetween(user,"Groceries",startDate, endDate);
        return grocery.stream().mapToDouble(Expense::getEamount).sum();
    }



    public double getBillsThisMonth() {
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();
        User user =userService. getCurrentUser();
        List<Expense> bills = expenseRepository.findByUserAndEcategoryAndEdateBetween(user,"Bills",startDate,endDate);
        return bills.stream().mapToDouble(Expense::getEamount).sum();
    }

    public double getBillsLastMonth() {
        LocalDate startDate = LocalDate.now().minusDays(60);
        LocalDate endDate = LocalDate.now().minusDays(31);
        User user = userService. getCurrentUser();
        List<Expense> bills = expenseRepository.findByUserAndEcategoryAndEdateBetween(user,"Bills",startDate, endDate);
        return bills.stream().mapToDouble(Expense::getEamount).sum();
    }



    public double getEntertaintmentThisMOnth() {
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();
        User user = userService. getCurrentUser();
        List<Expense> entertaintment = expenseRepository.findByUserAndEcategoryAndEdateBetween(user,"Entertaintment",startDate,endDate);
        return entertaintment.stream().mapToDouble(Expense::getEamount).sum();
    }

    public double getEntertaintmentlatMonth() {
        LocalDate startDate = LocalDate.now().minusDays(60);
        LocalDate endDate = LocalDate.now().minusDays(31);
        User user = userService. getCurrentUser();
        List<Expense> entertaintment = expenseRepository.findByUserAndEcategoryAndEdateBetween(user,"Entertaintment",startDate, endDate);
        return entertaintment.stream().mapToDouble(Expense::getEamount).sum();
    }


    public double getOthersThisMonth() {
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();
        User user = userService. getCurrentUser();
        List<Expense> others = expenseRepository.findByUserAndEcategoryAndEdateBetween(user,"Others",startDate,endDate);
        return others.stream().mapToDouble(Expense::getEamount).sum();
    }

    public double getOthersLastMonth() {
        LocalDate startDate = LocalDate.now().minusDays(60);
        LocalDate endDate = LocalDate.now().minusDays(31);
        User user = userService. getCurrentUser();
        List<Expense> others = expenseRepository.findByUserAndEcategoryAndEdateBetween(user,"Others",startDate, endDate);
        return others.stream().mapToDouble(Expense::getEamount).sum();
    }



    public Card getExpenseDifference() {
        Card card = new Card();
        double lastmonth = getLastMonth();
        double thismonth = getThisMonth();
        double difference =Math.abs(thismonth-lastmonth);
        double percentage = ((difference/lastmonth)*100);
        card.setPercentage(percentage);
        card.setTotal(getTotalExpense());
        if(thismonth < lastmonth)
        {
            card.setMessage("Decreased By "+difference);
        }

        else
            card.setMessage("Increased By " +difference);

        return card;
    }


    public Card CardForGroceries() {
        Card card = new Card();
        double lastmonth = getGroceriesLastMonth();
        double thismonth = getGroceriesThisMonth();
        double difference =Math.abs(thismonth-lastmonth);
        LocalDate startDate=LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();
        double total = expenseRepository.findSumByEcategoryAndUser("Groceries",startDate,endDate,userService.getCurrentUser());
        double percentage = 0;
        percentage=   (difference/lastmonth)*100;
        card.setPercentage(percentage);
        card.setTotal(total);
        if(thismonth < lastmonth)
        {
            card.setMessage("Decreased By "+difference);
        }

        else
            card.setMessage("Increased By " +difference);

        return card;
    }
    public Card cardForEntertaintment() {
        Card card = new Card();
        double lastmonth = getEntertaintmentlatMonth();
        double thismonth = getEntertaintmentThisMOnth();
        double difference =Math.abs(thismonth-lastmonth);
        LocalDate startDate=LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();
        double total = expenseRepository.findSumByEcategoryAndUser("Entertaintment",startDate,endDate,userService.getCurrentUser());
        double percentage = 0;
        percentage=   (difference/lastmonth)*100;
        card.setPercentage(percentage);
        card.setTotal(total);
        if(thismonth < lastmonth)
        {
            card.setMessage("Decreased By "+difference);
        }

        else
            card.setMessage("Increased By " +difference);

        return card;
    }    public Card cardForOthers() {
        Card card = new Card();
        double lastmonth = getOthersLastMonth();
        double thismonth = getOthersThisMonth();
        double difference =Math.abs(thismonth-lastmonth);
        LocalDate startDate=LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();
        double total = expenseRepository.findSumByEcategoryAndUser("Others",startDate,endDate,userService.getCurrentUser());
        double percentage = 0;
        percentage=   (difference/lastmonth)*100;
        card.setPercentage(percentage);
        card.setTotal(total);
        if(thismonth < lastmonth)
        {
            card.setMessage("Decreased By "+difference);
        }

        else
            card.setMessage("Increased By " +difference);

        return card;
    }    public Card cardForBills() {
        Card card = new Card();
        double lastmonth = getBillsLastMonth();
        double thismonth = getBillsThisMonth();
        double difference =Math.abs(thismonth-lastmonth);
        LocalDate startDate=LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();
        double total = expenseRepository.findSumByEcategoryAndUser("Bills",startDate,endDate,userService.getCurrentUser());
        double percentage = 0;
        percentage=   (difference/lastmonth)*100;
        card.setPercentage(percentage);
        card.setTotal(total);
        if(thismonth < lastmonth)
        {
            card.setMessage("Decreased By "+difference);
        }

        else
            card.setMessage("Increased By " +difference);

        return card;
    }

    public String deleteExpense(@PathVariable  int eid)
    {
        User user = userService.getCurrentUser();
        expenseRepository.deleteById(eid);
        return "Entry Deleted Successfully";
    }




}