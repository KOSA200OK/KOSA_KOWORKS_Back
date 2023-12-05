package com.my.notification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.my.notification.entity.NotificationEntity;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {

    @Query("SELECT n FROM NotificationEntity n WHERE n.receiverId.id = :receiverId")
    List<NotificationEntity> findAllByMemberEntity(@Param("receiverId") String receiverId);
} // end class
