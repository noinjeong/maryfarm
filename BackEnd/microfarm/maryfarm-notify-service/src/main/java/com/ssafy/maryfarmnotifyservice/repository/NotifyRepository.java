package com.ssafy.maryfarmnotifyservice.repository;

import com.ssafy.maryfarmnotifyservice.domain.notify.Notify;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifyRepository extends JpaRepository<Notify, String> {
}
