package com.vitaliy.forum.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vitaliy.forum.entity.Category;
import com.vitaliy.forum.entity.Post;
import com.vitaliy.forum.entity.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByCategory(Category category);
    List<Post> findByCategory_CategoryId(int categoryId);
    List<Post> findByAuthor(User author);
    List<Post> findByAuthor_UserId(int authorId);
    List<Post> findByIsActive(boolean isActive);
    List<Post> findByVisibility(boolean visibility);
    List<Post> findByTitleContaining(String keyword);
    List<Post> findByPostDateBetween(Date startDate, Date endDate);
    List<Post> findByIsActiveAndVisibility(boolean isActive, boolean visibility);
    List<Post> findByCategoryAndIsActive(Category category, boolean isActive);
    // Tìm bài viết active theo categoryId
    @Query("SELECT p FROM Post p WHERE p.category.categoryId = :categoryId AND p.isActive = true")
    List<Post> findActivePostsByCategoryId(@Param("categoryId") int categoryId);
    // Lấy tên category
    @Query("SELECT c.categoryName FROM Category c WHERE c.categoryId = :categoryId")
    Optional<String> findCategoryNameById(@Param("categoryId") int categoryId);

    @Query("SELECT p FROM Post p WHERE p.postId = :postId AND p.isActive = true")
    Optional<Post> findPostByActiveAndPostId(@Param("postId") int postId);

    @Modifying
    @Query("UPDATE Post p SET p.isActive = :isActive WHERE p.postId = :postId")
    int updateIsActiveById(@Param("postId") int postId, @Param("isActive") boolean isActive);

    @Query("SELECT c.categoryName, COUNT(p) FROM Post p JOIN p.category c GROUP BY c.categoryName")
    List<Object[]> findPostCountByCategory();
}