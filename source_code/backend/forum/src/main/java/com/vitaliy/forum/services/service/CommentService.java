package com.vitaliy.forum.services.service;

import java.util.List;
import com.vitaliy.forum.dto.CommentRequestDTO;
import com.vitaliy.forum.entity.Comment;
import com.vitaliy.forum.entity.User;

public interface CommentService {
    List<Comment> getCommentsByPostId(int postId);
    List<Comment> getCommentsByUserId(int userId);
    Comment getCommentById(int commentId);
    Comment createComment(int userId, CommentRequestDTO commentDTO);
    Comment updateComment(int commentId, String newComment, User currentUser);
    void deleteComment(int commentId, User currentUser);
    Boolean deleteCommentAdmin(int commentId);
    boolean isCommentExist(int commentId);
    void toggleCommentActiveStatus(int commentId, boolean isActive);
}
