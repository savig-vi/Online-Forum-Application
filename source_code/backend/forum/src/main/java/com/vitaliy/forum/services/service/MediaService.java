package com.vitaliy.forum.services.service;

import java.util.List;

import com.vitaliy.forum.entity.Comment;
import com.vitaliy.forum.entity.Media;
import com.vitaliy.forum.entity.Post;
import com.vitaliy.forum.entity.User;

public interface MediaService {
    Media saveMedia(Media media);
    Media getMediaById(int id);
    List<Media> getMediaByPost(Post post);
    List<Media> getMediaByComment(Comment comment);
    List<Media> getMediaByUploader(User user);
    void deleteMedia(int id);
}
