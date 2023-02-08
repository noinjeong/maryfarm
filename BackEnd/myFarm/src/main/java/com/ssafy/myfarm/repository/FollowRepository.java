package com.ssafy.myfarm.repository;

import com.ssafy.myfarm.domain.user.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, String> {

}
