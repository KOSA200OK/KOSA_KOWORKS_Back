package com.my.stuff.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.department.entity.DepartmentEntity;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.member.entity.MemberEntity;
import com.my.notification.entity.NotificationEntity;
import com.my.notification.service.NotificationServiceImpl;
import com.my.stuff.dto.StuffReqDTO;
import com.my.stuff.entity.StuffEntity;
import com.my.stuff.entity.StuffReqEntity;
import com.my.stuff.repository.StuffReqRepository;
import com.my.stuff.util.StuffReqMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StuffReqServiceImpl implements StuffReqService{
	@Autowired
	private StuffReqRepository sr;
	
	// 찬석
	@Autowired
	private NotificationServiceImpl notify;
	
	public void createStuffReq(StuffReqDTO dto) throws AddException {
		StuffReqEntity stuffReqEntity = StuffReqMapper.dtoToEntity(dto);
//		System.out.println("service: "+stuffReqEntity.getStuff().getId());
		sr.save(stuffReqEntity);
	}

	public List<StuffReqDTO> findByCase(String memberId, Long status, String stuffId, Date startDate, Date endDate)
	        throws FindException {

	    // 부울 변수를 사용하여 조건을 명확하게 정의
	    boolean isStatusDefault = status == 3;
	    boolean isStuffIdDefault = stuffId.equals("default");

	    // memberId 및 stuffId 설정
	    MemberEntity me = new MemberEntity();
	    me.setId(memberId);
	    StuffEntity se = new StuffEntity();
	    se.setId("%" + stuffId + "%");

	    // 결과 리스트 초기화
	    List<StuffReqDTO> srDTOList = new ArrayList<>();

	    // 조건에 따른 메서드 호출
	    if (isStatusDefault) {
	        handleStatusDefaultCase(isStuffIdDefault, me, se, startDate, endDate, srDTOList);
	    } else {
	        handleOtherCases(isStuffIdDefault, me, se, status, startDate, endDate, srDTOList);
	    }

	    return srDTOList;
	}

	private void handleStatusDefaultCase(boolean isStuffIdDefault, MemberEntity me, StuffEntity se, Date startDate, Date endDate, List<StuffReqDTO> srDTOList) {
	    if (isStuffIdDefault) {
	        // 날짜만 선택할 경우 - 요청상태는 전부
	        addEntitiesToDTOList(sr.findByMemberAndReqDateBetweenOrderByReqDateAsc(me, startDate, endDate), srDTOList);
	    } else {
	        // 날짜, 비품분류만 선택할 경우 - 요청상태는 전부
	        addEntitiesToDTOList(sr.findByMemberAndReqDateBetweenAndStuffLikeOrderByReqDateAsc(me, startDate, endDate, se), srDTOList);
	    }
	}

	private void handleOtherCases(boolean isStuffIdDefault, MemberEntity me, StuffEntity se, Long status, Date startDate, Date endDate, List<StuffReqDTO> srDTOList) {
	    if (isStuffIdDefault) {
	        // 날짜, 요청상태만 선택할 경우
	        addEntitiesToDTOList(sr.findByMemberAndReqDateBetweenAndStatusOrderByReqDateAsc(me, startDate, endDate, status), srDTOList);
	    } else {
	        // 날짜, 요청상태, 비품분류 전부를 선택했을 경우
	        addEntitiesToDTOList(sr.findByMemberAndReqDateBetweenAndStatusAndStuffLikeOrderByReqDateAsc(me, startDate, endDate, status, se), srDTOList);
	    }
	}

	private void addEntitiesToDTOList(List<StuffReqEntity> srEntityList, List<StuffReqDTO> srDTOList) {
	    for (StuffReqEntity stuffReqEntity : srEntityList) {
	        srDTOList.add(StuffReqMapper.entityToDto(stuffReqEntity));
	    }
	}
	
	
	public void removeById(Long id) {
		sr.deleteById(id);
	}
	
	@Override
	public Long findWaitProccessCnt(String memberId) throws FindException {
		MemberEntity me = MemberEntity.builder().id(memberId).build();
		Long status = 0L;
		Long waitProccessReqSize = (long) sr.findByMemberAndStatus(me, status).size();
		return waitProccessReqSize;
	}
	
	//==============================관리자용===================================================
	
	
	public List<StuffReqDTO> findByManageCase(Long departmentId, Long status, String stuffId, Date startDate, Date endDate)
	        throws FindException {

	    // 부울 변수를 사용하여 조건을 명확하게 정의
	    boolean isStatusDefault = status == 3;
	    boolean isStuffIdDefault = stuffId.equals("default");
	    boolean isDepartmentIdDefault = departmentId == 0;

	    // departmentId 및 stuffId 설정
	    DepartmentEntity de = DepartmentEntity.builder().id(departmentId).build();
	    StuffEntity se = new StuffEntity();
	    se.setId("%" + stuffId + "%");

	    // 결과 리스트 초기화
	    List<StuffReqDTO> srDTOList = new ArrayList<>();

	    // 메인 조건 분기
	    if (isStatusDefault) {
	        handleStatusDefaultCase(isStuffIdDefault, isDepartmentIdDefault, de, se, startDate, endDate, srDTOList);
	    } else {
	        handleOtherCases(isStuffIdDefault, isDepartmentIdDefault, de, se, status, startDate, endDate, srDTOList);
	    }

	    return srDTOList;
	}

	private void handleStatusDefaultCase(boolean isStuffIdDefault, boolean isDepartmentIdDefault, DepartmentEntity de, StuffEntity se, Date startDate, Date endDate, List<StuffReqDTO> srDTOList) {
	    if (isStuffIdDefault && isDepartmentIdDefault) {
	        // 날짜만 선택
	        findAndAddToDTOList(sr.findByReqDateBetweenOrderByReqDateAsc(startDate, endDate), srDTOList);
	    } else if (isStuffIdDefault) {
	        // 날짜, 부서 선택
	        findAndAddToDTOList(sr.findByMember_DepartmentAndReqDateBetweenOrderByReqDateAsc(de, startDate, endDate), srDTOList);
	    } else if (isDepartmentIdDefault) {
	        // 날짜, 비품 선택
	        findAndAddToDTOList(sr.findByStuffLikeAndReqDateBetweenOrderByReqDateAsc(se, startDate, endDate), srDTOList);
	    } else {
	        // 날짜, 부서, 비품
	        findAndAddToDTOList(sr.findByMember_DepartmentAndStuffLikeAndReqDateBetweenOrderByReqDateAsc(de, se, startDate, endDate), srDTOList);
	    }
	}

	private void handleOtherCases(boolean isStuffIdDefault, boolean isDepartmentIdDefault, DepartmentEntity de, StuffEntity se, Long status, Date startDate, Date endDate, List<StuffReqDTO> srDTOList) {
	    if (isStuffIdDefault && isDepartmentIdDefault) {
	        // 날짜, 요청상태 선택
	        findAndAddToDTOList(sr.findByStatusAndReqDateBetweenOrderByReqDateAsc(status, startDate, endDate), srDTOList);
	    } else if (isStuffIdDefault) {
	        // 날짜, 요청, 부서
	        findAndAddToDTOList(sr.findByStatusAndMember_DepartmentAndReqDateBetweenOrderByReqDateAsc(status, de, startDate, endDate), srDTOList);
	    } else if (isDepartmentIdDefault) {
	        // 날짜, 요청, 비품
	        findAndAddToDTOList(sr.findByStatusAndStuffLikeAndReqDateBetweenOrderByReqDateAsc(status, se, startDate, endDate), srDTOList);
	    } else {
	        // 날짜, 요청, 부서, 비품
	        findAndAddToDTOList(sr.findByMember_DepartmentAndStuffLikeAndStatusAndReqDateBetweenOrderByReqDateAsc(de, se, status, startDate, endDate), srDTOList);
	    }
	}

	private void findAndAddToDTOList(List<StuffReqEntity> srEntityList, List<StuffReqDTO> srDTOList) {
	    for (StuffReqEntity stuffReqEntity : srEntityList) {
	        srDTOList.add(StuffReqMapper.entityToDto(stuffReqEntity));
	    }
	}
	
	public StuffReqDTO findById(Long id) {
		Optional<StuffReqEntity> optS = sr.findById(id);
		StuffReqEntity se = optS.get();
		StuffReqDTO sd = StuffReqMapper.entityToDto(se);
		return sd;
	}
	
	public void modifyReqApprove(StuffReqDTO dto) throws ModifyException{
		Optional<StuffReqEntity> optS = sr.findById(dto.getId());
		StuffReqEntity se = optS.get();
        se.modifyStatus(dto.getStatus());
        sr.save(se);
        
        // 비품 승인 알림
    	// 찬석
		MemberEntity memberEntity = se.getMember();
        
		 notify.send(memberEntity, NotificationEntity.NotificationType.STUFF, "비품요청이 승인되었습니다.");
		
	}
	
	public void modifyReqReject(StuffReqDTO dto) throws ModifyException{
		Optional<StuffReqEntity> optS = sr.findById(dto.getId());
		StuffReqEntity se = optS.get();
        se.modifyStatus(dto.getStatus());
        se.modifyReject(dto.getReject());
        sr.save(se);
        
        // 비품 반려 알림
    	// 찬석

		MemberEntity memberEntity = se.getMember();
		log.warn("비품반려 id : {}", memberEntity.getId());
        
		 notify.send(memberEntity, NotificationEntity.NotificationType.STUFF, "비품요청이 반려되었습니다.");
        
	}

	@Override
	public Long findUnprocessedReqCnt() throws FindException {
		Long status = 0L;
		Long unprocessedReqSize = (long) sr.findByStatus(status).size();
		return unprocessedReqSize;
	}

}
