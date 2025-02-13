package com.vitaliy.forum.services.serviceImplement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitaliy.forum.entity.Media;
import com.vitaliy.forum.entity.Post;
import com.vitaliy.forum.entity.User;
import com.vitaliy.forum.repository.MediaRepository;
import com.vitaliy.forum.repository.PostRepository;
import com.vitaliy.forum.repository.UserRepository;
import com.vitaliy.forum.services.service.MediaService;

@Service
public class MediaServiceImpl implements MediaService {
    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    // Lấy tất cả media liên quan đến một bài viết
    public List<Media> getMediaByPost(int postId) {
        return mediaRepository.findByPost_PostId(postId);
    }

    // Lấy tất cả media của người dùng
    public List<Media> getMediaByUser(int userId) {
        return mediaRepository.findByUploadedBy_UserId(userId);
    }

    // Lấy một media theo ID
    public Media getMediaById(int mediaId) {
        Optional<Media> media = mediaRepository.findById(mediaId);
        return media.orElseThrow(() -> new RuntimeException("Media not found for id: " + mediaId));
    }

    // Tải lên một media mới
    public Media uploadMedia(int postId, int userId, String fileURL, boolean fileType) {
        Media media = new Media();
        Post post = postRepository.findById(postId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        media.setPost(post);  // Gán bài viết liên quan
        media.setUploadedBy(user);  // Gán người dùng tải lên
        media.setFileURL(fileURL);      // Gán URL media
        media.setFileType(fileType);    // Gán kiểu media (ví dụ: hình ảnh, video, v.v.)
        media.setUploadDate(new java.util.Date());  // Gán thời gian tải lên

        return mediaRepository.save(media);
    }

    // Xóa một media theo ID
    public void deleteMedia(int mediaId) {
        Media media = mediaRepository.findById(mediaId).orElseThrow(() -> new RuntimeException("Media not found for id: " + mediaId));
        mediaRepository.delete(media);
    }

    // Kiểm tra xem media có tồn tại không
    public boolean isMediaExist(int mediaId) {
        return mediaRepository.existsById(mediaId);
    }

    // Cập nhật thông tin của một media
    public Media updateMedia(int mediaId, String newFileURL, boolean newFileType) {
        Media media = mediaRepository.findById(mediaId).orElseThrow(() -> new RuntimeException("Media not found for id: " + mediaId));
        media.setFileURL(newFileURL);
        media.setFileType(newFileType);
        return mediaRepository.save(media);
    }
}
