package com.pennywisenepal.financetracker.Service;



import com.pennywisenepal.financetracker.Entity.Card;
import com.pennywisenepal.financetracker.Entity.Income;
import com.pennywisenepal.financetracker.Entity.User;
import com.pennywisenepal.financetracker.Repository.IncomeRepository;
import com.pennywisenepal.financetracker.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserDetailsService userDetailsService;
    private Double balance;


    public String addBalance(Income income)

    {
        User user = userRepository.findByUsername(userService.getcurrentusername());
        income.setIdate(LocalDate.now());
        income.setUser(user);
        incomeRepository.save(income);
       return "Income added Successfully";
    }



    public List<Income> getLastMonthList()
    {
        LocalDate startDate = LocalDate.now().minus(30, ChronoUnit.DAYS);
        LocalDate endDate = LocalDate.now().minus(1, ChronoUnit.DAYS);
        List<Income> incomes = incomeRepository.findByUserUsernameAndIdateBetweenOrderByIdateDesc(userService.getcurrentusername(),startDate, endDate);
        return incomes;

    }

    public List<Income> getLastWeekList()
    {

        LocalDate startDate = LocalDate.now().minus(7, ChronoUnit.DAYS);
        LocalDate endDate = LocalDate.now().minus(1, ChronoUnit.DAYS);
        List<Income> incomes = incomeRepository.findByUserUsernameAndIdateBetweenOrderByIdateDesc(userService.getcurrentusername(),startDate, endDate);

        return incomes;
    }








    public double getLastMonth() {
        LocalDate startDate = LocalDate.now().minus(60, ChronoUnit.DAYS);
        LocalDate endDate = LocalDate.now().minus(31, ChronoUnit.DAYS);
        List<Income> incomes = incomeRepository.findByUserUsernameAndIdateBetweenOrderByIdateDesc(userService.getcurrentusername(), startDate, endDate);
        return incomes.stream().mapToDouble(Income::getIamount).sum();
    }

    public double getSalaryThisMonth() {

        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();
        List<Income> salary = incomeRepository.findByUserUsernameAndIcategoryAndIdateBetween(userService.getcurrentusername(),"Salary",startDate, endDate);
        return salary.stream().mapToDouble(Income::getIamount).sum();
    }

    public double getSalaryLastMonth() {

        LocalDate startDate = LocalDate.now().minusDays(60);
        LocalDate endDate = LocalDate.now().minusDays(31);
        List<Income> salary = incomeRepository.findByUserUsernameAndIcategoryAndIdateBetween(userService.getcurrentusername(),"Salary",startDate, endDate);
        return salary.stream().mapToDouble(Income::getIamount).sum();
    }


    public double getInvestmentThisMonth() {

        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();
        List<Income> salary = incomeRepository.findByUserUsernameAndIcategoryAndIdateBetween(userService.getcurrentusername(), "Investments",startDate, endDate);
        return salary.stream().mapToDouble(Income::getIamount).sum();
    }

    public double getInvestmentLastMonth() {
        LocalDate startDate = LocalDate.now().minusDays(60);
        LocalDate endDate = LocalDate.now().minusDays(31);
        List<Income> salary = incomeRepository.findByUserUsernameAndIcategoryAndIdateBetween(userService.getcurrentusername(), "Investments",startDate, endDate);
        return salary.stream().mapToDouble(Income::getIamount).sum();
    }


    public double getOtherThisMonth() {
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();
        List<Income> salary = incomeRepository.findByUserUsernameAndIcategoryAndIdateBetween(userService.getcurrentusername(), "Other",startDate, endDate);
        return salary.stream().mapToDouble(Income::getIamount).sum();
    }

    public double getOtherLastMonth() {
        LocalDate startDate = LocalDate.now().minusDays(60);
        LocalDate endDate = LocalDate.now().minusDays(31);
        List<Income> salary = incomeRepository.findByUserUsernameAndIcategoryAndIdateBetween(userService.getcurrentusername(), "Other",startDate, endDate);
        return salary.stream().mapToDouble(Income::getIamount).sum();
    }

    public double getRealstateThisMonth() {

        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();
        List<Income> salary = incomeRepository.findByUserUsernameAndIcategoryAndIdateBetween(userService.getcurrentusername(), "Real Estate",startDate, endDate);
        return salary.stream().mapToDouble(Income::getIamount).sum();
    }

    public double getRealstateLastMonth() {
        LocalDate startDate = LocalDate.now().minusDays(60);
        LocalDate endDate = LocalDate.now().minusDays(31);
        List<Income> salary = incomeRepository.findByUserUsernameAndIcategoryAndIdateBetween(userService.getcurrentusername(), "Real Estate",startDate, endDate);
        return salary.stream().mapToDouble(Income::getIamount).sum();
    }






    public double getThisMonth() {
        LocalDate startDate = LocalDate.now().minus(30, ChronoUnit.DAYS);
        LocalDate endDate = LocalDate.now();
        List<Income> incomes = incomeRepository.findByUserUsernameAndIdateBetweenOrderByIdateDesc(userService.getcurrentusername(), startDate, endDate);
        return incomes.stream().mapToDouble(Income::getIamount).sum();
    }






    public Card getIncomeDifference() {
        Card card = new Card();
        double lastmonth = getLastMonth();
        double thismonth = getThisMonth();
        double difference =Math.abs(thismonth-lastmonth);
        double percentage = ((difference/lastmonth)*100);
        balance=incomeRepository.getIncomeLastThirtyDaysForUserUsername(userService.getcurrentusername(),LocalDate.now().minusDays(30));
        card.setPercentage(percentage);
        card.setTotal( balance != null ? balance : 0.0);
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
        double totalSalary = incomeRepository.findSumByIcategoryAndUserUsername("Salary",startDate,endDate,userService.getcurrentusername());
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
        double totalInvestment = incomeRepository.findSumByIcategoryAndUserUsername("Investments",startDate,endDate,userService.getcurrentusername());
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
        double totalOthers = incomeRepository.findSumByIcategoryAndUserUsername("Other",startDate,endDate, userService.getcurrentusername());
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
        double totalRs = incomeRepository.findSumByIcategoryAndUserUsername("RealEstate",startDate,endDate,userService.getcurrentusername());
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






















