package com.vitaliy.forum.services.service;

import java.util.List;

import com.vitaliy.forum.dto.post.CreatePostRequestDTO;
import com.vitaliy.forum.dto.post.UpdatePostRequestDTO;
import com.vitaliy.forum.entity.Post;
import com.vitaliy.forum.entity.User;
import com.vitaliy.forum.exception.BusinessException;


public interface PostService {
    Post getPostById(Integer postId);
    List<Post> getPostsByCategoryId(Integer categoryId);
    List<Post> getPostsByAuthorId(int authorId);
    Post createPost(CreatePostRequestDTO request, User author);
    Post updatePost(int postId, UpdatePostRequestDTO request, int userId) throws BusinessException;
    int deletePost(int postId, int userId) throws BusinessException;
    int deletePostAdmin(int postId) throws BusinessException;
    Post togglePostVisibility(int postId, boolean visibility);
    Post togglePostStatus(int postId, boolean isActive);
    int updateIsActiveById(int postId, Boolean isActive);
}
