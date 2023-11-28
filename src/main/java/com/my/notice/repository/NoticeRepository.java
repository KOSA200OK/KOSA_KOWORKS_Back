package com.my.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.notice.entity.NoticeEntity;


public interface NoticeRepository extends JpaRepository<NoticeEntity, Long>{

}
