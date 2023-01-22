package com.ssafy.myfarm.repository;

import com.ssafy.myfarm.domain.chat.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, String> {
}
