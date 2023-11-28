package com.my.notice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.my.notice.entity.NoticeEntity;


public interface NoticeRepository extends JpaRepository<NoticeEntity, Long>{
	
//	@Query(value="SELECT m.name as name, n.member_id as memberId, n.title as title, n.content as content, n.regdate as regdate \r\n"
//			+ "FROM notice n JOIN member m ON n.member_id = m.id \r\n"
//			+ "ORDER BY regdate DESC",
//			nativeQuery=true)
	public Page<NoticeEntity> findAll(Pageable pageable);
	
}
