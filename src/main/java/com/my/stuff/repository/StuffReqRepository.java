package com.my.stuff.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.my.department.entity.DepartmentEntity;
import com.my.member.entity.MemberEntity;
import com.my.stuff.entity.StuffEntity;
import com.my.stuff.entity.StuffReqEntity;

@Repository
public interface StuffReqRepository extends JpaRepository<StuffReqEntity, Long> {
	
	// 비품요청내역 조회 ===========================================================================================================
	
//	/**
//	 * 멤버
//	 * stuff_req 테이블에서 memberId에 해당하는 행을 조회하여 반환한다
//	 * @param member
//	 * @return List<StuffReqEntity>
//	 */
//	List<StuffReqEntity> findByMember(MemberEntity member);
	
	/**
	 * 멤버, 요청상태
	 * @param member
	 * @param status
	 * @return
	 */
	List<StuffReqEntity> findByMemberAndStatus(MemberEntity member, Long status);
	
	/**
	 * 멤버, 날짜, 요청상태
	 * stuff_req 테이블에서 멤버id, 날짜 구간, 특정 status에 해당하는 리스트를 반환한다.
	 * @param member
	 * @param startDate
	 * @param endDate
	 * @return <StuffReqEntity>
	 */
	List<StuffReqEntity> findByMemberAndReqDateBetweenAndStatusOrderByReqDateAsc(MemberEntity member, Date startDate, Date endDate, Long status);
	
	/**
	 * 멤버, 날짜
	 * stuff_req 테이블에서 멤버id, 날짜 구간에 해당하는 리스트를 반환한다.
	 * @param member
	 * @param startDate
	 * @param endDate
	 * @return <StuffReqEntity>
	 */
	List<StuffReqEntity> findByMemberAndReqDateBetweenOrderByReqDateAsc(MemberEntity member, Date startDate, Date endDate);
	
	
    /**
     * 멤버, 날짜, 요청상태, 비품분류
     * @param member
     * @param startDate
     * @param endDate
     * @param status
     * @param stuff
     * @return
     */
	List<StuffReqEntity> findByMemberAndReqDateBetweenAndStatusAndStuffLikeOrderByReqDateAsc(MemberEntity member, Date startDate, Date endDate, Long status, StuffEntity stuff);
	
    /**
     * 멤버, 날짜, 비품분류
     * @param member
     * @param startDate
     * @param endDate
     * @param startS
     * @param endS
     * @param stuff
     * @return
     */
	List<StuffReqEntity> findByMemberAndReqDateBetweenAndStuffLikeOrderByReqDateAsc(MemberEntity member, Date startDate, Date endDate, StuffEntity stuff);
	
	// 비품요청내역 조회 끝 ===========================================================================================================
	
	// 관리자용 비품요청내역 조회  ===========================================================================================================
	
	/**
	 * stuff_req 테이블에서 status 값에 따라 조회한다
	 * @param status
	 * @return
	 */
	List<StuffReqEntity> findByStatus(Long status);
	
	/**
	 * stuff_req 테이블에서 status 값에 따라 조회하고 내림차순 정렬한다
	 * @param status
	 * @return
	 */
	List<StuffReqEntity> findByStatusOrderByReqDateAsc(Long status);
	
	/**
	 * stuff_req 테이블에서 날짜 구간별 필터링
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<StuffReqEntity> findByReqDateBetweenOrderByReqDateAsc (Date startDate, Date endDate);

    /**
     * 날짜. 요청상태
     * @param startDate
     * @param endDate
     * @param status
     * @return
     */
	List<StuffReqEntity> findByStatusAndReqDateBetweenOrderByReqDateAsc (Long status, Date startDate, Date endDate);
	
	/**
	 * 날짜. 부서
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<StuffReqEntity> findByMember_DepartmentAndReqDateBetweenOrderByReqDateAsc (DepartmentEntity department, Date startDate, Date endDate);
	
	/**
	 * 날짜. 비품
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<StuffReqEntity> findByStuffLikeAndReqDateBetweenOrderByReqDateAsc (StuffEntity stuff, Date startDate, Date endDate);
	
	/**
	 * 날짜.요청.부서
	 * @param startDate
	 * @param endDate
	 * @param department
	 * @param status
	 * @return
	 */
	List<StuffReqEntity> findByStatusAndMember_DepartmentAndReqDateBetweenOrderByReqDateAsc(Long status, DepartmentEntity department, Date startDate, Date endDate);
	
	
	/**
	 * 날짜.요청.비품
	 * @param startDate
	 * @param endDate
	 * @param department
	 * @param status
	 * @return
	 */
	List<StuffReqEntity> findByStatusAndStuffLikeAndReqDateBetweenOrderByReqDateAsc(Long status, StuffEntity stuff, Date startDate, Date endDate);
	

	/**
	 * 날짜.부서.비품
	 * @param startDate
	 * @param endDate
	 * @param department
	 * @param status
	 * @return
	 */
	List<StuffReqEntity> findByMember_DepartmentAndStuffLikeAndReqDateBetweenOrderByReqDateAsc(DepartmentEntity department, StuffEntity stuff, Date startDate, Date endDate);
	
	/**
	 * 날짜.요청. 부서.비품
	 * @param startDate
	 * @param endDate
	 * @param department
	 * @param status
	 * @return
	 */
	List<StuffReqEntity> findByMember_DepartmentAndStuffLikeAndStatusAndReqDateBetweenOrderByReqDateAsc(
			DepartmentEntity department, StuffEntity stuff, Long status, Date startDate, Date endDate);
	

    
}
