package com.vitaliy.forum.services.serviceImplement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitaliy.forum.entity.Follow;
import com.vitaliy.forum.entity.User;
import com.vitaliy.forum.repository.FollowRepository;
import com.vitaliy.forum.repository.UserRepository;
import com.vitaliy.forum.services.service.FollowService;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    // Lấy danh sách người dùng mà một người theo dõi
    public List<Follow> getFollowedUsersByFollower(int followerId) {
        return followRepository.findByFollower_UserId(followerId);
    }

    // Lấy danh sách người theo dõi của một người dùng
    public List<Follow> getFollowersByFollowed(int followedId) {
        return followRepository.findByFollowed_UserId(followedId);
    }

    // Kiểm tra xem người dùng đã theo dõi người khác hay chưa
    public boolean isUserFollowing(int followerId, int followedId) {
        return followRepository.existsByFollower_UserIdAndFollowed_UserId(followerId, followedId);
    }

    // Người dùng theo dõi người khác
    public Follow followUser(int followerId, int followedId) {
        // Kiểm tra nếu người dùng đã theo dõi rồi, tránh việc theo dõi lại
        if (isUserFollowing(followerId, followedId)) {
            throw new RuntimeException("User already follows this person");
        }

        Follow follow = new Follow();
        User follower = userRepository.findById(followerId).orElse(null);
        User followed = userRepository.findById(followedId).orElse(null);
        follow.setFollower(follower);  // Thiết lập người theo dõi
        follow.setFollowed(followed);  // Thiết lập người được theo dõi
        follow.setFollowDate(new java.util.Date());  // Thiết lập thời gian theo dõi

        return followRepository.save(follow);
    }

    // Người dùng hủy theo dõi người khác
    public void unfollowUser(int followerId, int followedId) {
        Follow follow = followRepository.findByFollower_UserIdAndFollowed_UserId(followerId, followedId);
        if (follow != null) {
            followRepository.delete(follow);
        } else {
            throw new RuntimeException("Follow relationship not found");
        }
    }

    // Lấy một mối quan hệ theo dõi cụ thể
    public Follow getFollowById(int followId) {
        Optional<Follow> follow = followRepository.findById(followId);
        return follow.orElseThrow(() -> new RuntimeException("Follow relationship not found for id: " + followId));
    }

    // Kiểm tra mối quan hệ theo dõi có tồn tại hay không
    public boolean isFollowExist(int followId) {
        return followRepository.existsById(followId);
    }
}
