package com.vitaliy.forum.services.service;

import java.util.List;

import com.vitaliy.forum.entity.Media;

public interface MediaService {
    List<Media> getMediaByPost(int postId);
    List<Media> getMediaByUser(int userId);
    Media getMediaById(int mediaId);
    Media uploadMedia(int postId, int userId, String mediaUrl, boolean mediaType);
    void deleteMedia(int mediaId);
    boolean isMediaExist(int mediaId);
    Media updateMedia(int mediaId, String newFileURL, boolean newFileType);
}
