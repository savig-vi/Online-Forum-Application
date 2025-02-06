package com.vitaliy.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitaliy.forum.entity.Follow;
import com.vitaliy.forum.entity.User;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {
    List<Follow> findByFollowed(User followed);
    List<Follow> findByFollower(User follower);
    Follow findByFollowerAndFollowed(User follower, User followed);
}
