package com.vitaliy.forum.services.serviceImplement;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vitaliy.forum.dto.post.CreatePostRequestDTO;
import com.vitaliy.forum.dto.post.UpdatePostRequestDTO;
import com.vitaliy.forum.entity.Category;
import com.vitaliy.forum.entity.Post;
import com.vitaliy.forum.entity.User;
import com.vitaliy.forum.exception.BusinessException;
import com.vitaliy.forum.repository.CategoryRepository;
import com.vitaliy.forum.repository.PostRepository;
import com.vitaliy.forum.services.service.PostService;


@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public Post getPostById(Integer postId) {
        Optional<Post> post = postRepository.findById(postId);
        return post.orElseThrow(() -> new RuntimeException("Post not found for id: " + postId));
    }

    @Override
    public List<Post> getPostsByCategoryId(Integer categoryId) {
        return postRepository.findByCategory_CategoryId(categoryId);
    }

    @Override
    public List<Post> getPostsByAuthorId(int authorId) {
        return postRepository.findByAuthor_UserId(authorId);
    }

    @Override
    @Transactional
    public Post createPost(CreatePostRequestDTO request, User author) {
        // Lấy category từ database
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chủ đề với ID: " + request.getCategoryId()));

        // Tạo entity mới
        Post newPost = new Post();
        newPost.setTitle(request.getTitle());
        newPost.setContent(request.getContent());
        newPost.setCategory(category);
        newPost.setAuthor(author);
        newPost.setPostDate(new Date());
        newPost.setVisibility(true); // Mặc định public
        newPost.setActive(true); // Mặc định active

        return postRepository.save(newPost);
    }

    @Override
    @Transactional
    public Post updatePost(int postId, UpdatePostRequestDTO request, int userId) throws BusinessException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException("Bài viết không tồn tại", HttpStatus.NOT_FOUND));

        if (post.getAuthor().getUserId() != userId) {
            throw new BusinessException("Không có quyền chỉnh sửa", HttpStatus.FORBIDDEN);
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public int deletePost(int postId, int userId) throws BusinessException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException("Bài viết không tồn tại", HttpStatus.NOT_FOUND));

        if (post.getAuthor().getUserId() != userId) {
            throw new BusinessException("Không có quyền xóa", HttpStatus.FORBIDDEN);
        }

        // post.setActive(false);
        postRepository.deleteById(postId);
        int postCategoryId = post.getCategory().getCategoryId();
        return postCategoryId;
    }

    @Override
    @Transactional
    public int deletePostAdmin(int postId) throws BusinessException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException("Bài viết không tồn tại", HttpStatus.NOT_FOUND));

        // post.setActive(false);
        postRepository.deleteById(postId);
        int postCategoryId = post.getCategory().getCategoryId();
        return postCategoryId;
    }

    @Override
    @Transactional
    public int updateIsActiveById(int postId, Boolean isActive) {
        return postRepository.updateIsActiveById(postId, isActive);
    }

    @Override
    public Post togglePostVisibility(int postId, boolean visibility) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found for id: " + postId));

        post.setVisibility(visibility);
        return postRepository.save(post); // Lưu trạng thái hiển thị đã thay đổi
    }

    @Override
    public Post togglePostStatus(int postId, boolean isActive) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found for id: " + postId));

        post.setActive(isActive);
        return postRepository.save(post); // Lưu trạng thái hoạt động đã thay đổi
    }
}