package com.vitaliy.forum.services.serviceImplement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitaliy.forum.dto.CommentResponse;
import com.vitaliy.forum.dto.PostDetailResponse;
import com.vitaliy.forum.entity.Category;
import com.vitaliy.forum.entity.Comment;
import com.vitaliy.forum.entity.Post;
import com.vitaliy.forum.entity.User;
import com.vitaliy.forum.repository.CommentRepository;
import com.vitaliy.forum.repository.PostRepository;
import com.vitaliy.forum.services.service.PostService;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post getPostById(int id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public List<Post> getPostsByCategoryId(Category category) {
        return postRepository.findByCategoryId(category);
    }

    @Override
    public List<Post> getPostsByAuthor(User authorId) {
        return postRepository.findByAuthorId(authorId);
    }

    @Override
    public List<Post> getPostsByVisibility(Boolean visibility) {
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
    public void togglePostVisibility(int id, Boolean visibility) {
        Post post = getPostById(id);
        if (post != null) {
            post.setVisibility(visibility);
            updatePost(post);
        }
    }

    // PostDetailResponse getPostDetailById(Long postId)

    @Override
    public PostDetailResponse getPostDetailById(Integer postId) {
        // Lấy bài viết từ database
        
        Post post = postRepository.findById(postId).orElse(new Post());


        List<Comment> comments = commentRepository.findByPostId(post);

        // Chuyển dữ liệu từ Post và Comment thành PostDetailResponse
        PostDetailResponse response = new PostDetailResponse();
        response.setPostId(post.getPostId());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        response.setAuthorName(post.getAuthorId().getFullName()); // Lấy tên người đăng từ đối tượng Author
        response.setCategoryName(post.getCategoryId().getCategoryName()); // Lấy tên thể loại từ đối tượng Category
        response.setPostDate(post.getPostDate());
        
        // Map các bình luận
        // ĐỂ Ý CÁI STREAM NÀY, HƠI KHÓ HIỂU
        response.setComments(comments.stream()
                                      .map(comment -> new CommentResponse(comment.getAuthorId().getFullName(), comment.getContent()))
                                      .collect(Collectors.toList()));
        return response;
    }
}
