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

    @Query("SELECT SUM(e.eamount) FROM Expense e WHERE e.user.username = :username AND e.edate >= :thirtyDaysAgo")
    double getExpenseLastThirtyDaysForUserUsername(@Param("username") String username, @Param("thirtyDaysAgo") LocalDate thirtyDaysAgo);

    List<Expense> findByUserUsernameAndEdateBetweenOrderByEdateDesc(String username, LocalDate startDate, LocalDate endDate);

    List<Expense> findByUserUsernameAndEcategoryAndEdateBetween(String username, String ecategory, LocalDate startDate, LocalDate endDate);

    @Query("SELECT COALESCE(SUM(e.eamount), 0) FROM Expense e WHERE e.ecategory = :ecategory AND e.edate BETWEEN :startDate AND :endDate AND e.user.username = :username")
    double findSumByEcategoryAndUserUsername(@Param("ecategory") String ecategory, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("username") String username);

    @Query("DELETE FROM Expense e WHERE e.eid = :eid AND e.user.username = :username")
    void deleteByEidAndUserUsername(@Param("eid") int eid, @Param("username") String username);

}