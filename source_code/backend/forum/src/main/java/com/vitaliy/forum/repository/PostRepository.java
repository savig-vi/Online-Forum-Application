package com.vitaliy.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitaliy.forum.entity.Category;
import com.vitaliy.forum.entity.Post;
import com.vitaliy.forum.entity.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByCategory(Category category);
    List<Post> findByAuthor(User author);
    List<Post> findByVisibility(String visibility);
}
