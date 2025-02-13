package com.vitaliy.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitaliy.forum.entity.Comment;
import com.vitaliy.forum.entity.Media;
import com.vitaliy.forum.entity.Post;
import com.vitaliy.forum.entity.User;

@Repository
public interface MediaRepository extends JpaRepository<Media, Integer> {
    List<Media> findByPost(Post post);
    List<Media> findByPost_PostId(int postId);
    List<Media> findByUploadedBy(User user);
    List<Media> findByUploadedBy_UserId(int userId);
    List<Media> findByComment(Comment comment);
}