package com.gmail.woosay333.onlinebookstore.repository.user;

import com.gmail.woosay333.onlinebookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByEmail(String email);
}
