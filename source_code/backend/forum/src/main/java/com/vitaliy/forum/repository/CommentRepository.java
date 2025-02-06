package com.vitaliy.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitaliy.forum.entity.Comment;
import com.vitaliy.forum.entity.Post;
import com.vitaliy.forum.entity.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPost(Post post);
    List<Comment> findByAuthor(User author);
}
