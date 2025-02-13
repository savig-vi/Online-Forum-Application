package com.vitaliy.forum.services.serviceImplement;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitaliy.forum.dto.CommentRequestDTO;
import com.vitaliy.forum.entity.Comment;
import com.vitaliy.forum.entity.Post;
import com.vitaliy.forum.entity.User;
import com.vitaliy.forum.exception.ResourceNotFoundException;
import com.vitaliy.forum.repository.CommentRepository;
import com.vitaliy.forum.repository.PostRepository;
import com.vitaliy.forum.repository.UserRepository;
import com.vitaliy.forum.services.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    // Lấy tất cả bình luận theo bài viết
    public List<Comment> getCommentsByPostId(int postId) {
        return commentRepository.findByPost_PostId(postId);
    }

    // Lấy tất cả bình luận theo người dùng
    public List<Comment> getCommentsByUserId(int userId) {
        return commentRepository.findByCommenter_UserId(userId);
    }

    // Lấy bình luận theo ID
    public Comment getCommentById(int commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        return comment.orElseThrow(() -> new RuntimeException("Comment not found for id: " + commentId));
    }

    // Tạo mới bình luận
    public Comment createComment(int userId, CommentRequestDTO commentDTO) {
        Optional<Post> postOpt = postRepository.findById(commentDTO.getPostId());
        Optional<User> userOpt = userRepository.findById(userId);

        if (postOpt.isEmpty()) {
            throw new RuntimeException("Bài viết không tồn tại!");
        }
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Người dùng không tồn tại!");
        }

        Comment newComment = new Comment();
        newComment.setPost(postOpt.get());
        newComment.setCommenter(userOpt.get());
        newComment.setContent(commentDTO.getContent());
        newComment.setCommentDate(new Date());
        newComment.setActive(true);

        return commentRepository.save(newComment);
    }

    // Cập nhật bình luận
    public Comment updateComment(int commentId, String newContent, User currentUser) {
        Comment comment = commentRepository.findByCommentIdAndCommenter(commentId, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found or unauthorized"));
        
        comment.setContent(newContent);
        return commentRepository.save(comment);
    }

    public void deleteComment(int commentId, User currentUser) {
        Comment comment = commentRepository.findByCommentIdAndCommenter(commentId, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found or unauthorized"));
        
        commentRepository.delete(comment);
    }

    public Boolean deleteCommentAdmin(int commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            commentRepository.delete(comment.get());
            return true;
        }
        return false;
    }


    // Kiểm tra xem bình luận có tồn tại hay không
    public boolean isCommentExist(int commentId) {
        return commentRepository.existsById(commentId);
    }

    // Cập nhật trạng thái "IsActive" của bình luận (có thể là khóa hoặc kích hoạt bình luận)
    public void toggleCommentActiveStatus(int commentId, boolean isActive) {
        Comment comment = getCommentById(commentId);
        comment.setActive(isActive);
        commentRepository.save(comment);
    }
}
