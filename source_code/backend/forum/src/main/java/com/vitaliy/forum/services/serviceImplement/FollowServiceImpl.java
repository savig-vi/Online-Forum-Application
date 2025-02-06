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
        follow.setFollower(follower);
        follow.setFollowed(followed);
        followRepository.save(follow);
    }

    @Override
    public void unfollowUser(User follower, User followed) {
        Follow follow = followRepository.findByFollowerAndFollowed(follower, followed);
        if (follow != null) {
            followRepository.delete(follow);
        }
    }

    @Override
    public List<Follow> getFollowers(User followed) {
        return followRepository.findByFollowed(followed);
    }

    @Override
    public List<Follow> getFollowing(User follower) {
        return followRepository.findByFollower(follower);
    }
}
