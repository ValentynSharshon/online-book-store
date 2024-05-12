package com.gmail.woosay333.onlinebookstore.repository.role;

import com.gmail.woosay333.onlinebookstore.entity.Role;
import com.gmail.woosay333.onlinebookstore.entity.RoleName;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> getAllByNameIn(Set<RoleName> roleNames);
}
