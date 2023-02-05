package com.ssafy.maryfarmuserservice.repository.jpa;

import com.ssafy.maryfarmuserservice.domain.user.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, String> {

}
