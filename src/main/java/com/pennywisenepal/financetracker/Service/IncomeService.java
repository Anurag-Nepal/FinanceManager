package com.pennywisenepal.financetracker.Service;



import com.pennywisenepal.financetracker.Entity.Card;
import com.pennywisenepal.financetracker.Entity.Income;
import com.pennywisenepal.financetracker.Entity.User;
import com.pennywisenepal.financetracker.Repository.IncomeRepository;
import com.pennywisenepal.financetracker.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    private double  balance;



    private String getCurrentToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (String) authentication.getDetails();
        }
        return null;
    }

    public User getCurrentUser() {
        // Assuming Spring Security is used to authenticate users
        {
            String username = jwtService.extractUserName(getCurrentToken());
            System.out.println(username);
            {
                return userRepository.findByUsername(username);
            }
        }
    }

    public void addBalance(Income income)

    {
        User user = getCurrentUser();
        income.setIdate(LocalDate.now());
        income.setUser(user);
        incomeRepository.save(income);
        new ResponseEntity<>(income, HttpStatus.OK);
    }

    public double getTotalBalance()
    {
        User user = getCurrentUser();
        balance=incomeRepository.getIncomeLastThirtyDaysForUser(user,LocalDate.now().minusDays(30));
        return balance;
    }


    public List<Income> getLastMonthList()
    {
        User user = getCurrentUser();
        LocalDate startDate = LocalDate.now().minus(30, ChronoUnit.DAYS);
        LocalDate endDate = LocalDate.now().minus(1, ChronoUnit.DAYS);
        List<Income> incomes = incomeRepository.findByUserAndIdateBetweenOrderByIdateDesc(user,startDate, endDate);
        return incomes;

    }

    public List<Income> getLastWeekList()
    {
        User user = getCurrentUser();
        LocalDate startDate = LocalDate.now().minus(7, ChronoUnit.DAYS);
        LocalDate endDate = LocalDate.now().minus(1, ChronoUnit.DAYS);
        List<Income> incomes = incomeRepository.findByUserAndIdateBetweenOrderByIdateDesc(user,startDate, endDate);
        return incomes;

    }





    public double getLastMonth() {
        User user = getCurrentUser();
        LocalDate startDate = LocalDate.now().minus(60, ChronoUnit.DAYS);
        LocalDate endDate = LocalDate.now().minus(31, ChronoUnit.DAYS);
        List<Income> incomes = incomeRepository.findByUserAndIdateBetweenOrderByIdateDesc(user,startDate, endDate);
        return incomes.stream().mapToDouble(Income::getIamount).sum();
    }

    public double getSalaryThisMonth() {
        User user = getCurrentUser();
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();
        List<Income> salary = incomeRepository.findByUserAndIcategoryAndIdateBetween(user,"Salary",startDate, endDate);
        return salary.stream().mapToDouble(Income::getIamount).sum();
    }

    public double getSalaryLastMonth() {
        User user = getCurrentUser();
        LocalDate startDate = LocalDate.now().minusDays(60);
        LocalDate endDate = LocalDate.now().minusDays(31);
        List<Income> salary = incomeRepository.findByUserAndIcategoryAndIdateBetween(user,"Salary",startDate, endDate);
        return salary.stream().mapToDouble(Income::getIamount).sum();
    }


    public double getInvestmentThisMonth() {
        User user = getCurrentUser();
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();
        List<Income> salary = incomeRepository.findByUserAndIcategoryAndIdateBetween(user,"Investments",startDate, endDate);
        return salary.stream().mapToDouble(Income::getIamount).sum();
    }

    public double getInvestmentLastMonth() {
        User user = getCurrentUser();
        LocalDate startDate = LocalDate.now().minusDays(60);
        LocalDate endDate = LocalDate.now().minusDays(31);
        List<Income> salary = incomeRepository.findByUserAndIcategoryAndIdateBetween(user,"Investments",startDate, endDate);
        return salary.stream().mapToDouble(Income::getIamount).sum();
    }


    public double getOtherThisMonth() {
        User user = getCurrentUser();
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();
        List<Income> salary = incomeRepository.findByUserAndIcategoryAndIdateBetween(user,"Other",startDate, endDate);
        return salary.stream().mapToDouble(Income::getIamount).sum();
    }

    public double getOtherLastMonth() {
        User user = getCurrentUser();
        LocalDate startDate = LocalDate.now().minusDays(60);
        LocalDate endDate = LocalDate.now().minusDays(31);
        List<Income> salary = incomeRepository.findByUserAndIcategoryAndIdateBetween(user,"Other",startDate, endDate);
        return salary.stream().mapToDouble(Income::getIamount).sum();
    }

    public double getRealstateThisMonth() {
        User user = getCurrentUser();
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();
        List<Income> salary = incomeRepository.findByUserAndIcategoryAndIdateBetween(user,"Real Estate",startDate, endDate);
        return salary.stream().mapToDouble(Income::getIamount).sum();
    }

    public double getRealstateLastMonth() {
        User user = getCurrentUser();
        LocalDate startDate = LocalDate.now().minusDays(60);
        LocalDate endDate = LocalDate.now().minusDays(31);
        List<Income> salary = incomeRepository.findByUserAndIcategoryAndIdateBetween(user,"Real Estate",startDate, endDate);
        return salary.stream().mapToDouble(Income::getIamount).sum();
    }






    public double getThisMonth() {
        User user = getCurrentUser();
        LocalDate startDate = LocalDate.now().minus(30, ChronoUnit.DAYS);
        LocalDate endDate = LocalDate.now();
        List<Income> incomes = incomeRepository.findByUserAndIdateBetweenOrderByIdateDesc(user,startDate, endDate);
        return incomes.stream().mapToDouble(Income::getIamount).sum();
    }






    public Card getIncomeDifference() {
        Card card = new Card();
        double lastmonth = getLastMonth();
        double thismonth = getThisMonth();
        double difference =Math.abs(thismonth-lastmonth);
        double percentage = ((difference/lastmonth)*100);
        card.setPercentage(percentage);
        card.setTotal(getTotalBalance());
        if(thismonth < lastmonth)
        {
            card.setMessage("Decreased By "+difference);
        }

        else
            card.setMessage("Increased By " +difference);

        return card;
    }







    public Card cardforSalary() {
        Card card = new Card();
        double lastmonth = getSalaryLastMonth();
        double thismonth = getSalaryThisMonth();
        double difference =Math.abs(thismonth-lastmonth);
        LocalDate startDate=LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();
        double totalSalary = incomeRepository.findSumByIcategory("Salary",startDate,endDate);
        double percentage = 0;
        percentage=   (difference/lastmonth)*100;
        card.setPercentage(percentage);
        card.setTotal(totalSalary);
        if(thismonth < lastmonth)
        {
            card.setMessage("Decreased By "+difference);
        }

        else
            card.setMessage("Increased By " +difference);

        return card;
    }

    public Card cardforInvestment() {
        Card card = new Card();
        double lastmonth = getInvestmentLastMonth();
        double thismonth = getInvestmentThisMonth();
        double difference =Math.abs(thismonth-lastmonth);
        LocalDate startDate=LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();
        double totalInvestment = incomeRepository.findSumByIcategory("Investments",startDate,endDate);
        double percentage = 0;
        percentage=   (difference/lastmonth)*100;
        card.setPercentage(percentage);
        card.setTotal(totalInvestment);
        if(thismonth < lastmonth)
        {
            card.setMessage("Decreased By "+difference);
        }

        else
            card.setMessage("Increased By " +difference);

        return card;
    }
    public Card cardforOthers() {
        Card card = new Card();
        double lastmonth = getOtherLastMonth();
        double thismonth = getOtherThisMonth();
        double difference =Math.abs(thismonth-lastmonth);
        LocalDate startDate=LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();
        double totalOthers = incomeRepository.findSumByIcategory("Other",startDate,endDate);
        double percentage = 0;
        percentage=   (difference/lastmonth)*100;
        card.setPercentage(percentage);
        card.setTotal(totalOthers);
        if(thismonth < lastmonth)
        {
            card.setMessage("Decreased By "+difference);
        }

        else
            card.setMessage("Increased By " +difference);

        return card;
    }
    public Card cardforRealstate() {
        Card card = new Card();
        double lastmonth = getRealstateLastMonth();
        double thismonth = getRealstateThisMonth();
        double difference =Math.abs(thismonth-lastmonth);
        LocalDate startDate=LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();
        double totalRs = incomeRepository.findSumByIcategory("Real Estate",startDate,endDate);
        double percentage = 0;
        percentage=   (difference/lastmonth)*100;
        card.setPercentage(percentage);
        card.setTotal(totalRs);
        if(thismonth < lastmonth)
        {
            card.setMessage("Decreased By "+difference);
        }

        else
            card.setMessage("Increased By " +difference);

        return card;
    }




    public void deleteIncome(int iid)
    {
        incomeRepository.deleteById(iid);
    }





}






















