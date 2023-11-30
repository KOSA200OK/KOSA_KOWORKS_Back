package com.my.stuff.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.my.member.entity.MemberEntity;
import com.my.stuff.entity.StuffEntity;
import com.my.stuff.entity.StuffReqEntity;

@Repository
public interface StuffReqRepository extends JpaRepository<StuffReqEntity, Long> {

	/**
	 * 멤버 id를 기준으로 해당 멤버가 작성한 비품요청 전체 목록을 반환한다
	 * 
	 * @param member
	 * @return List<StuffReqEntity>
	 */
	List<StuffReqEntity> findByMember(MemberEntity member);
	
	List<StuffReqEntity> findByMemberAndStuffLike(MemberEntity member, StuffEntity stuff);
	
	

}
