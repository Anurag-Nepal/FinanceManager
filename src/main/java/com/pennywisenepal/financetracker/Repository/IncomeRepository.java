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



    LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);

    @Query("SELECT SUM(i.iamount) FROM Income i WHERE i.user.username = :username AND i.idate >= :thirtyDaysAgo")
    double getIncomeLastThirtyDaysForUserUsername(@Param("username") String username, @Param("thirtyDaysAgo") LocalDate thirtyDaysAgo);

    List<Income> findByUserUsernameAndIdateBetweenOrderByIdateDesc(String username, LocalDate startDate, LocalDate endDate);

    List<Income> findByUserUsernameAndIcategoryAndIdateBetween(String username, String icategory, LocalDate startDate, LocalDate endDate);

    @Query("SELECT COALESCE(SUM(i.iamount), 0) FROM Income i WHERE i.icategory = :icategory AND i.idate BETWEEN :startDate AND :endDate AND i.user.username = :username")
    double findSumByIcategoryAndUserUsername(@Param("icategory") String icategory, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("username") String username);

}
















