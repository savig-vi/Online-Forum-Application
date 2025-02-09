package com.vitaliy.forum.services.serviceImplement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitaliy.forum.entity.Follow;
import com.vitaliy.forum.entity.User;
import com.vitaliy.forum.repository.FollowRepository;
import com.vitaliy.forum.services.service.FollowService;

@Service
public class FollowServiceImpl implements FollowService {
@Autowired
    private FollowRepository followRepository;

    @Override
    public void followUser(User follower, User followed) {
        Follow follow = new Follow();
        follow.setFollowerId(follower);
        follow.setFollowedId(followed);
        followRepository.save(follow);
    }

    @Override
    public void unfollowUser(User followerId, User followedId) {
        Follow follow = followRepository.findByFollowerIdAndFollowedId(followerId, followedId);
        if (follow != null) {
            followRepository.delete(follow);
        }
    }

    @Override
    public List<Follow> getFollowers(User followedId) {
        return followRepository.findByFollowedId(followedId);
    }

    @Override
    public List<Follow> getFollowing(User followerId) {
        return followRepository.findByFollowerId(followerId);
    }
}
