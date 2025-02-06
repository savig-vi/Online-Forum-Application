package com.vitaliy.forum.services.service;

import java.util.List;

import com.vitaliy.forum.entity.Comment;
import com.vitaliy.forum.entity.Post;
import com.vitaliy.forum.entity.User;

public interface CommentService {
    Comment saveComment(Comment comment);
    Comment getCommentById(int id);
    List<Comment> getCommentsByPost(Post post);
    List<Comment> getCommentsByAuthor(User author);
    void updateComment(Comment comment);
    void deleteComment(int id);
    void toggleCommentVisibility(int id, boolean isActive);
}
