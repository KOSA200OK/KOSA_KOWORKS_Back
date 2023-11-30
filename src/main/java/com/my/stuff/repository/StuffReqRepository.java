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
	 * stuff_req 테이블에서 사용자가 작성한 비품요청 행을 조회하여 반환한다
	 * @param member
	 * @return List<StuffReqEntity>
	 */
	List<StuffReqEntity> findByMember(MemberEntity member);
	
	/**
	 * stuff_req 테이블에서 사용자가 작성한 비품 요청 행 중 stuffId에 인자로 넘겨진 문자열이 포함된 행들을 조회하여 반환한다 
	 * @param MemberEntity, StuffEntity
	 * @return <StuffReqEntity>
	 */
	List<StuffReqEntity> findByMemberAndStuffLike(MemberEntity member, StuffEntity stuff);
//	List<StuffReqEntity> findByMemberAndStuffContaining(MemberEntity member, StuffEntity stuff);
	
	

}
