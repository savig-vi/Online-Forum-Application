package com.vitaliy.forum.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vitaliy.forum.entity.Category;
import com.vitaliy.forum.entity.User;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByCategoryName(String categoryName);
    List<Category> findByCreatedBy(User createdBy);
    boolean existsByCategoryName(String categoryName);
    void deleteByCategoryName(String categoryName);
    // Truy vấn tên category theo ID
    @Query("SELECT c.categoryName FROM Category c WHERE c.categoryId = :categoryId")
    Optional<String> findCategoryNameById(@Param("categoryId") int categoryId);
}
