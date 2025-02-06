package com.vitaliy.forum.services.serviceImplement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitaliy.forum.entity.Category;
import com.vitaliy.forum.entity.Post;
import com.vitaliy.forum.entity.User;
import com.vitaliy.forum.entity._enum.Visibility;
import com.vitaliy.forum.repository.PostRepository;
import com.vitaliy.forum.services.service.PostService;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post getPostById(int id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public List<Post> getPostsByCategory(Category category) {
        return postRepository.findByCategory(category);
    }

    @Override
    public List<Post> getPostsByAuthor(User author) {
        return postRepository.findByAuthor(author);
    }

    @Override
    public List<Post> getPostsByVisibility(String visibility) {
        return postRepository.findByVisibility(visibility);
    }

    @Override
    public void updatePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void deletePost(int id) {
        postRepository.deleteById(id);
    }

    @Override
    public void togglePostVisibility(int id, Visibility visibility) {
        Post post = getPostById(id);
        if (post != null) {
            post.setVisibility(visibility);
            updatePost(post);
        }
    }
}
