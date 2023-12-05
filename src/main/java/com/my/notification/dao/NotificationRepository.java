package com.my.notification.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.notification.entity.NotificationEntity;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {

} // end class
