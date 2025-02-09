package com.vitaliy.forum.services.service;

import java.util.List;

import com.vitaliy.forum.dto.PostDetailResponse;
import com.vitaliy.forum.entity.Category;
import com.vitaliy.forum.entity.Post;
import com.vitaliy.forum.entity.User;


public interface PostService {
    Post savePost(Post post);
    Post getPostById(int id);
    List<Post> getPostsByCategoryId(Category category);
    List<Post> getPostsByAuthor(User author);
    List<Post> getPostsByVisibility(Boolean visibility);
    void updatePost(Post post);
    void deletePost(int id);
    void togglePostVisibility(int id, Boolean visibility);
    PostDetailResponse getPostDetailById(Integer postId);
}
