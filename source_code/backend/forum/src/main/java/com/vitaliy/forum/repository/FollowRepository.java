package com.vitaliy.forum.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitaliy.forum.entity.Follow;
import com.vitaliy.forum.entity.User;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {
    List<Follow> findByFollower(User follower);
    List<Follow> findByFollower_UserId(int followerId);
    List<Follow> findByFollowed(User followed);
    List<Follow> findByFollowed_UserId(int followedId);
    Follow findByFollower_UserIdAndFollowed_UserId(int followerId, int followedId);
    boolean existsByFollower_UserIdAndFollowed_UserId(int followerId, int followedId);
    Optional<Follow> findByFollowerAndFollowed(User follower, User followed);
    List<Follow> findByFollowDate(Date followDate);
    void deleteByFollowerAndFollowed(User follower, User followed);
    List<Follow> findByFollowerOrFollowed(User follower, User followed);
    List<Follow> findTop10ByOrderByFollowDateDesc();
}
