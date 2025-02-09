package com.vitaliy.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitaliy.forum.entity.Follow;
import com.vitaliy.forum.entity.User;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {
    List<Follow> findByFollowedId(User followedId);
    List<Follow> findByFollowerId(User followerId);
    Follow findByFollowerIdAndFollowedId(User followerId, User followedId);
}
