package com.pennywisenepal.financetracker.Repository;


import com.pennywisenepal.financetracker.Entity.Expense;
import com.pennywisenepal.financetracker.Entity.Income;
import com.pennywisenepal.financetracker.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income,Integer> {



//    @Query("SELECT SUM(i.iamount) FROM Income i")
//    Double getTotalBalance();

    LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
    @Query("SELECT SUM(i.iamount) FROM Income i WHERE i.user = :user AND i.idate >= :thirtyDaysAgo")
    double getIncomeLastThirtyDaysForUser(@Param("user") User user, @Param("thirtyDaysAgo") LocalDate thirtyDaysAgo);


    List<Income> findByUserAndIdateBetweenOrderByIdateDesc(User user, LocalDate startDate, LocalDate endDate);



    //    List<Income> findSalaryByIdateGreaterThanEqualAndIdateLessThanEqual(LocalDate startDate, LocalDate endDate);
    List<Income> findByUserAndIcategoryAndIdateBetween(User user, String icategory, LocalDate startDate, LocalDate endDate);


    @Query("SELECT COALESCE(SUM(i.iamount), 0) FROM Income i WHERE i.icategory = :icategory AND i.idate BETWEEN :startDate AND :endDate")
    double findSumByIcategory(String icategory,LocalDate startDate,LocalDate endDate);





















}