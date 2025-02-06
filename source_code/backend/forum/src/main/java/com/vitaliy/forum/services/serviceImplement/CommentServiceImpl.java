package com.vitaliy.forum.services.serviceImplement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitaliy.forum.entity.Comment;
import com.vitaliy.forum.entity.Post;
import com.vitaliy.forum.entity.User;
import com.vitaliy.forum.repository.CommentRepository;
import com.vitaliy.forum.services.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment getCommentById(int id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Comment> getCommentsByPost(Post post) {
        return commentRepository.findByPost(post);
    }

    @Override
    public List<Comment> getCommentsByAuthor(User author) {
        return commentRepository.findByAuthor(author);
    }

    @Override
    public void updateComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(int id) {
        commentRepository.deleteById(id);
    }

    @Override
    public void toggleCommentVisibility(int id, boolean isActive) {
        Comment comment = getCommentById(id);
        if (comment != null) {
            comment.setActive(isActive);
            updateComment(comment);
        }
    }
}
