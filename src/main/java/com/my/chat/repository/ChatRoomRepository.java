package com.my.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.chat.entity.ChatRoomEntity;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {

}
