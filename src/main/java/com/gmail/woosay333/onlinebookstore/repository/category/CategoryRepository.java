package com.gmail.woosay333.onlinebookstore.repository.category;

import com.gmail.woosay333.onlinebookstore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsCategoryById(Long id);
}
