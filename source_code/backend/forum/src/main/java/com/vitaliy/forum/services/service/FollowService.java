package com.vitaliy.forum.services.service;

import java.util.List;
import com.vitaliy.forum.entity.Follow;

public interface FollowService {
    List<Follow> getFollowedUsersByFollower(int followerId);
    List<Follow> getFollowersByFollowed(int followedId);
    boolean isUserFollowing(int followerId, int followedId);
    Follow followUser(int followerId, int followedId);
    void unfollowUser(int followerId, int followedId);
    Follow getFollowById(int followId);
    boolean isFollowExist(int followId);
}
