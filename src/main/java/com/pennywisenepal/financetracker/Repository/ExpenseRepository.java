package com.pennywisenepal.financetracker.Repository;

import com.pennywisenepal.financetracker.Entity.Expense;
import com.pennywisenepal.financetracker.Entity.Income;
import com.pennywisenepal.financetracker.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense,Integer> {


    LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
//    @Query("SELECT SUM(i.eamount) FROM Expense i WHERE i.edate >= :thirtyDaysAgo")
//    double  getExpenseLastThirtyDays(@Param("thirtyDaysAgo") LocalDate thirtyDaysAgo);

    @Query("SELECT SUM(i.eamount) FROM Expense i WHERE i.user = :user AND i.edate >= :thirtyDaysAgo")
    double getExpenseLastThirtyDaysForUser(@Param("user") User user, @Param("thirtyDaysAgo") LocalDate thirtyDaysAgo);


    //    List<Expense> findByEdateGreaterThanEqualAndEdateLessThanEqual(LocalDate startDate, LocalDate endDate);
    List<Expense> findByUserAndEdateBetweenOrderByEdateDesc(User user, LocalDate startDate, LocalDate endDate);




    //    List<Expense> findByEcategoryAndEdateBetween(String ecategory,LocalDate startDate, LocalDate endDate);
    List<Expense> findByUserAndEcategoryAndEdateBetween(User user, String ecategory, LocalDate startDate, LocalDate endDate);




    @Query("SELECT COALESCE(SUM(e.eamount), 0) FROM Expense e WHERE e.ecategory = :ecategory AND e.edate BETWEEN :startDate AND :endDate AND e.user = :user")
    double findSumByEcategoryAndUser(String ecategory, LocalDate startDate, LocalDate endDate, User user);




    @Query("DELETE FROM Expense e WHERE e.eid = :eid AND e.user = :user")
    void deleteByIdAndUser(int eid, User user);
}