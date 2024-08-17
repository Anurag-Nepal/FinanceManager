package com.pennywisenepal.financetracker.Repository;


import com.pennywisenepal.financetracker.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    User findByUsername(String username);

    User findByOtp(int otp);
}