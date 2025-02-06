package com.vitaliy.forum.services.service;

import java.util.List;

import com.vitaliy.forum.entity.Follow;
import com.vitaliy.forum.entity.User;

public interface FollowService {
    void followUser(User follower, User followed);
    void unfollowUser(User follower, User followed);
    List<Follow> getFollowers(User followed);
    List<Follow> getFollowing(User follower);
}
