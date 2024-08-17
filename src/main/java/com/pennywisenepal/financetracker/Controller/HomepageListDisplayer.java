package com.pennywisenepal.financetracker.Controller;



import com.pennywisenepal.financetracker.Entity.Card;
import com.pennywisenepal.financetracker.Entity.Expense;
import com.pennywisenepal.financetracker.Entity.Income;

import com.pennywisenepal.financetracker.Service.ExpenseService;
import com.pennywisenepal.financetracker.Service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class HomepageListDisplayer {

    @Autowired
    private final ExpenseService expenseService;
    @Autowired
    private final IncomeService incomeService;

    public HomepageListDisplayer(ExpenseService expenseService, IncomeService incomeService) {
        this.expenseService = expenseService;
        this.incomeService = incomeService;
    }


    //Sorting this list is required and remanining
    @GetMapping("/random")
    public List<Object> random1(@PathVariable(required = false) Integer deleteId) {
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
        List<Object> sortedItems = new ArrayList<>();
        for (SortableWrapper wrapper : wrappers) {
            sortedItems.add(wrapper.getItem());
        }


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
}


