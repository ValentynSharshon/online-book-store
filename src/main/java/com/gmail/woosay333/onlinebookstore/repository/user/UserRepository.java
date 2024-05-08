package com.gmail.woosay333.onlinebookstore.repository.user;

import com.gmail.woosay333.onlinebookstore.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
}
