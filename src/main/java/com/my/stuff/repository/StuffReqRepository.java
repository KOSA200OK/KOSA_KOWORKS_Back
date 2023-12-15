package com.my.stuff.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.my.department.entity.DepartmentEntity;
import com.my.member.entity.MemberEntity;
import com.my.stuff.entity.StuffEntity;
import com.my.stuff.entity.StuffReqEntity;

@Repository
public interface StuffReqRepository extends JpaRepository<StuffReqEntity, Long> {
	
	// 비품요청내역 조회 ===========================================================================================================
	
	/**
	 * 멤버
	 * stuff_req 테이블에서 memberId에 해당하는 행을 조회하여 반환한다
	 * @param member
	 * @return List<StuffReqEntity>
	 */
	List<StuffReqEntity> findByMember(MemberEntity member);
	
	
	/**
	 * 멤버, 날짜구간
	 * stuff_req 테이블에서 사용자가 작성한 비품 요청 행 중 입력한 날짜 사이에 해당하는 행의 리스트를 반환한다.
	 * @param member
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<StuffReqEntity> findByMemberAndReqDateBetween(MemberEntity member, Date startDate, Date endDate);
	
	/**
	 * 멤버, 요청상태
	 * @param member
	 * @param status
	 * @return
	 */
	List<StuffReqEntity> findByMemberAndStatus(MemberEntity member, Long status);
	
	/**
	 * 멤버, 요청상태 구간
	 * @param member
	 * @param status
	 * @return
	 */
	List<StuffReqEntity> findByMemberAndStatusBetween(MemberEntity member, Long startS, Long endS);
	
	/**
	 * 멤버, 비품분류
	 * stuff_req 테이블에서 사용자가 작성한 비품 요청 행 중 stuffId에 인자로 넘겨진 문자열이 포함된 행들을 조회하여 반환한다 
	 * @param MemberEntity, StuffEntity
	 * @return <StuffReqEntity>
	 */
	List<StuffReqEntity> findByMemberAndStuffLike(MemberEntity member, StuffEntity stuff);
	
	/**
	 * 멤버, 날짜, 요청상태
	 * stuff_req 테이블에서 멤버id, 날짜 구간, 특정 status에 해당하는 리스트를 반환한다.
	 * @param member
	 * @param startDate
	 * @param endDate
	 * @return <StuffReqEntity>
	 */
	List<StuffReqEntity> findByMemberAndReqDateBetweenAndStatus(MemberEntity member, Date startDate, Date endDate, Long status);
	
	/**
	 * 멤버, 날짜, 요청상태구간
	 * stuff_req 테이블에서 멤버id, 날짜 구간, status구간에 해당하는 리스트를 반환한다.
	 * @param member
	 * @param startDate
	 * @param endDate
	 * @return <StuffReqEntity>
	 */
	List<StuffReqEntity> findByMemberAndReqDateBetweenAndStatusBetween(MemberEntity member, Date startDate, Date endDate, Long startS, Long endS);
	
    /**
     * 멤버, 날짜, 비품분류
     * @param member
     * @param startDate
     * @param endDate
     * @param stuff
     * @return
     */
	List<StuffReqEntity> findByMemberAndReqDateBetweenAndStuffLike(MemberEntity member, Date startDate, Date endDate, StuffEntity stuff);
	
    /**
     * 멤버, 날짜, 요청상태, 비품분류
     * @param member
     * @param startDate
     * @param endDate
     * @param status
     * @param stuff
     * @return
     */
	List<StuffReqEntity> findByMemberAndReqDateBetweenAndStatusAndStuffLike(MemberEntity member, Date startDate, Date endDate, Long status, StuffEntity stuff);
	
    /**
     * 멤버, 날짜, 요청상태구간, 비품분류
     * @param member
     * @param startDate
     * @param endDate
     * @param startS
     * @param endS
     * @param stuff
     * @return
     */
	List<StuffReqEntity> findByMemberAndReqDateBetweenAndStatusBetweenAndStuffLike(MemberEntity member, Date startDate, Date endDate, Long startS, Long endS, StuffEntity stuff);
	
	/**
	 * stuff_req 테이블에서 사용자가 작성한 비품 요청 행 중 stuffId에 압력한 문자열이포함되는 경우, 모든 status 경우를 반환한다
	 * @param MemberEntity, StuffEntity status
	 * @return <StuffReqEntity>
	 */
	List<StuffReqEntity> findByMemberAndStatusBetweenAndStuffLike(MemberEntity member, Long startS, Long endS, StuffEntity stuff);
	
	
	/**
	 * stuff_req 테이블에서 사용자가 작성한 비품 요청 행 중 stuffId에 압력한 문자열이포함되는 경우, status값이 일치하는 경우를 반환한다
	 * @param MemberEntity, StuffEntity status
	 * @return <StuffReqEntity>
	 */
	List<StuffReqEntity> findByMemberAndStatusAndStuffLike(MemberEntity member, Long status, StuffEntity stuff);
	
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
	List<StuffReqEntity> findByStatusOrderByReqDateDesc(Long status);
	
	
	/**
	 * stuff_req 테이블에서 부서별 필터링
	 * @param department
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<StuffReqEntity> findByMember_Department(DepartmentEntity department);	
	
	/**
	 * stuff_req 테이블에서 날짜 구간별 필터링
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<StuffReqEntity> findByReqDateBetween (Date startDate, Date endDate);

    /**
     * 날짜. 요청상태
     * @param startDate
     * @param endDate
     * @param status
     * @return
     */
	List<StuffReqEntity> findByStatusAndReqDateBetween (Long status, Date startDate, Date endDate);
	
    /**
     * 날짜. 요청상태구간
     * @param startDate
     * @param endDate
     * @param startS
     * @param endS
     * @return
     */
	List<StuffReqEntity> findByStatusBetweenAndReqDateBetween (Date startDate, Date endDate, Long startS, Long endS);
	
	/**
	 * 날짜. 부서
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<StuffReqEntity> findByMember_DepartmentAndReqDateBetween (DepartmentEntity department, Date startDate, Date endDate);
	
	/**
	 * 날짜. 비품
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<StuffReqEntity> findByStuffLikeAndReqDateBetween (StuffEntity stuff, Date startDate, Date endDate);
	
	/**
	 * 날짜.요청.부서
	 * @param startDate
	 * @param endDate
	 * @param department
	 * @param status
	 * @return
	 */
	List<StuffReqEntity> findByStatusAndMember_DepartmentAndReqDateBetween(Long status, DepartmentEntity department, Date startDate, Date endDate);
	
	/**
	 * 날짜.요청구간.부서
	 * @param startDate
	 * @param endDate
	 * @param department
	 * @param status
	 * @return
	 */
	List<StuffReqEntity> findByStatusBetweenAndMember_DepartmentAndReqDateBetween(
			Date startDate, Date endDate, DepartmentEntity department, Long startS, Long endS);
	
	/**
	 * 날짜.요청.비품
	 * @param startDate
	 * @param endDate
	 * @param department
	 * @param status
	 * @return
	 */
	List<StuffReqEntity> findByStatusAndStuffLikeAndReqDateBetween(Long status, StuffEntity stuff, Date startDate, Date endDate);
	
	/**
	 * 날짜.요청구간.비품
	 * @param startDate
	 * @param endDate
	 * @param department
	 * @param status
	 * @return
	 */
	List<StuffReqEntity> findByStatusBetweenAndStuffLikeAndReqDateBetween(
			Date startDate, Date endDate, StuffEntity stuff, Long startS, Long endS);
	
	/**
	 * 날짜.부서.비품
	 * @param startDate
	 * @param endDate
	 * @param department
	 * @param status
	 * @return
	 */
	List<StuffReqEntity> findByMember_DepartmentAndStuffLikeAndReqDateBetween(DepartmentEntity department, StuffEntity stuff, Date startDate, Date endDate);
	
	/**
	 * 날짜.요청. 부서.비품
	 * @param startDate
	 * @param endDate
	 * @param department
	 * @param status
	 * @return
	 */
	List<StuffReqEntity> findByMember_DepartmentAndStuffLikeAndStatusAndReqDateBetween(
			DepartmentEntity department, StuffEntity stuff, Long status, Date startDate, Date endDate);
	
	/**
	 * 날짜.요청구간. 부서.비품
	 * @param startDate
	 * @param endDate
	 * @param department
	 * @param status
	 * @return
	 */
	List<StuffReqEntity> findByMember_DepartmentAndStuffLikeAndStatusBetweenAndReqDateBetween(Date startDate, Date endDate, 
			                                                                       StuffEntity stuff, DepartmentEntity department,
			                                                                       Long startS, Long endS);

    
}
