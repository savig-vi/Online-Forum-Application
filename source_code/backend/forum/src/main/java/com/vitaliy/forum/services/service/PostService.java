package com.vitaliy.forum.services.service;

import java.util.List;

import com.vitaliy.forum.entity.Category;
import com.vitaliy.forum.entity.Post;
import com.vitaliy.forum.entity.User;
import com.vitaliy.forum.entity._enum.Visibility;

public interface PostService {
    Post savePost(Post post);
    Post getPostById(int id);
    List<Post> getPostsByCategory(Category category);
    List<Post> getPostsByAuthor(User author);
    List<Post> getPostsByVisibility(String visibility);
    void updatePost(Post post);
    void deletePost(int id);
    void togglePostVisibility(int id, Visibility visibility);
}
