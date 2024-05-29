package com.gmail.woosay333.onlinebookstore.repository.category;

import com.gmail.woosay333.onlinebookstore.entity.Category;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Set<Category> findAllByIdIn(Set<Long> ids);
}
