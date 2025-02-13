package com.vitaliy.forum.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitaliy.forum.entity.Comment;
import com.vitaliy.forum.entity.Post;
import com.vitaliy.forum.entity.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPost_PostId(int postId);
    List<Comment> findByPost(Post post);
    List<Comment> findByCommenter_UserId(int commenterId);
    List<Comment> findByCommenter(User commenter);
    List<Comment> findByIsActive(boolean isActive);
    List<Comment> findByPostAndIsActive(Post post, boolean isActive);
    Optional<Comment> findByCommentIdAndCommenter(int commentId, User commenter);
}