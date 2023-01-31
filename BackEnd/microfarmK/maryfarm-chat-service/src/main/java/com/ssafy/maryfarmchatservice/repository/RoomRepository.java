package com.ssafy.maryfarmchatservice.repository;

import com.ssafy.maryfarmchatservice.domain.chat.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, String> {
}
