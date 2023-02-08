package com.ssafy.myfarm.repository;

import com.ssafy.myfarm.domain.user.Notify;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifyRepository extends JpaRepository<Notify, String> {
}
