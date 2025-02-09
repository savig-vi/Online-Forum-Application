package com.vitaliy.forum.services.serviceImplement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitaliy.forum.entity.Comment;
import com.vitaliy.forum.entity.Media;
import com.vitaliy.forum.entity.Post;
import com.vitaliy.forum.entity.User;
import com.vitaliy.forum.repository.MediaRepository;
import com.vitaliy.forum.services.service.MediaService;

@Service
public class MediaServiceImpl implements MediaService {
    @Autowired
    private MediaRepository mediaRepository;

    @Override
    public Media saveMedia(Media media) {
        return mediaRepository.save(media);
    }

    @Override
    public Media getMediaById(int id) {
        return mediaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Media> getMediaByPost(Post postId) {
        return mediaRepository.findByPostId(postId);
    }

    @Override
    public List<Media> getMediaByComment(Comment commentId) {
        return mediaRepository.findByCommentId(commentId);
    }

    @Override
    public List<Media> getMediaByUploader(User userId) {
        return mediaRepository.findByUploadedById(userId);
    }

    @Override
    public void deleteMedia(int id) {
        mediaRepository.deleteById(id);
    }
}
