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
    
	public List<StuffReqDTO> findByMemberId(String memberId) throws FindException {
		MemberEntity me = MemberEntity.builder().id(memberId).build();
		List<StuffReqEntity> srEntityList = sr.findByMember(me);
		List<StuffReqDTO> srDTOList = new ArrayList<>();
		for (StuffReqEntity stuffReqEntity : srEntityList) {
			StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
			srDTOList.add(srDTO);
		}

		return srDTOList;
	}

	public List<StuffReqDTO> findByCase(String memberId, Long status, String stuffId, Date startDate, Date endDate)
			throws FindException {

		// memberId = 필수, Date = 필수, status = 0, 1, 2, 선택 안할경우 3 , stuffId = %s%, 선택
		// 안할경우 default

		// memberId 세팅
		MemberEntity me = new MemberEntity();
		me.setId(memberId);
 
		// stuffId 가공
		String stuffIdM = new String();
		stuffIdM = "%" + stuffId + "%";
		log.error("stuffNameM={}", stuffIdM);
		StuffEntity se = new StuffEntity();
		se.setId(stuffIdM);

		// 전달할 리스트 세팅
		List<StuffReqDTO> srDTOList = new ArrayList<>();
		
		//조건에 따른 메서드 호출

		if(status == 3) {// 날짜만 선택할 경우 - 요청상태는 전부
			if(stuffId.equals("default")) {
				List<StuffReqEntity> srEntityList = sr.findByMemberAndReqDateBetweenOrderByReqDateAsc(me, startDate, endDate);
				for (StuffReqEntity stuffReqEntity : srEntityList) {
					StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
					srDTOList.add(srDTO);
				}
				return srDTOList;
			
			}else { // 날짜, 비품분류만 선택할 경우 - 요청상태는 전부
				log.error("stuffId = {}", stuffId);
				log.error("case = 3");
				List<StuffReqEntity> srEntityList = sr.findByMemberAndReqDateBetweenAndStuffLikeOrderByReqDateAsc(me, startDate, endDate, se);
				for (StuffReqEntity stuffReqEntity : srEntityList) {
					StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
					srDTOList.add(srDTO);
				}
				return srDTOList;
			}
		}else {
			if(stuffId.equals("default")) { // 날짜, 요청상태만 선택할 경우
				List<StuffReqEntity> srEntityList = sr.findByMemberAndReqDateBetweenAndStatusOrderByReqDateAsc(me, startDate, endDate,
						status);
				for (StuffReqEntity stuffReqEntity : srEntityList) {
					StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
					srDTOList.add(srDTO);
				}
				return srDTOList;
			}else { //날짜, 요청상태, 비품분류 전부를 선택했을 경우
				log.error("case = 4");
				List<StuffReqEntity> srEntityList = sr.findByMemberAndReqDateBetweenAndStatusAndStuffLikeOrderByReqDateAsc(me, startDate, endDate, status, se);
				for (StuffReqEntity stuffReqEntity : srEntityList) {
					StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
					srDTOList.add(srDTO);
				}
				
				return srDTOList;
			}			
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

		// Date = 필수 / status = 0, 1, 2, 선택 안할경우 3 /stuffId = %s%, 선택 안할경우 default
        
		//departmentId 세팅
		DepartmentEntity de = DepartmentEntity.builder()
        .id(departmentId)
        .build();
 
		// stuffId 가공
		String stuffIdM = new String();
		stuffIdM = "%" + stuffId + "%";
		log.error("stuffNameM={}", stuffIdM);
		StuffEntity se = new StuffEntity();
		se.setId(stuffIdM);

		// 전달할 리스트 세팅
		List<StuffReqDTO> srDTOList = new ArrayList<>();
		
		//조건에 따른 메세드 호출
		
		if ( (status ==3) && (stuffId.equals("default")) && (departmentId == 0) ) { //날짜만 선택
			log.error("case = 1");
			List<StuffReqEntity> srEntityList = sr.findByReqDateBetweenOrderByReqDateAsc(startDate, endDate);
			for (StuffReqEntity stuffReqEntity : srEntityList) {
				StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
				srDTOList.add(srDTO);
			}
			log.error("size=" + srDTOList.size());
			return srDTOList;
			
		} else if ( (stuffId.equals("default")) && (departmentId == 0) ) { // 날짜. 요청상태 선택
			log.error("case = 2");
			List<StuffReqEntity> srEntityList = sr.findByStatusAndReqDateBetweenOrderByReqDateAsc(status, startDate, endDate);
			for (StuffReqEntity stuffReqEntity : srEntityList) {
				StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
				srDTOList.add(srDTO);
			}
			log.error("size=" + srDTOList.size());
			return srDTOList;
			
		} else if ( (status ==3) && (stuffId.equals("default")) ){ //날짜. 부서 선택
			log.error("case = 3");
			List<StuffReqEntity> srEntityList = sr.findByMember_DepartmentAndReqDateBetweenOrderByReqDateAsc(de, startDate, endDate);
			for (StuffReqEntity stuffReqEntity : srEntityList) {
				StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
				srDTOList.add(srDTO);
			}
			log.error("size=" + srDTOList.size());
			return srDTOList;
			
		} else if ( (status ==3) && (departmentId == 0) ) { //날짜. 비품 선택
			log.error("case = 4");
			List<StuffReqEntity> srEntityList = sr.findByStuffLikeAndReqDateBetweenOrderByReqDateAsc(se, startDate, endDate);
			for (StuffReqEntity stuffReqEntity : srEntityList) {
				StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
				srDTOList.add(srDTO);
			}
			log.error("size=" + srDTOList.size());
			return srDTOList;	
			
		} else if ( stuffId.equals("default") ) { //날짜. 요청. 부서
			log.error("case = 5");
			List<StuffReqEntity> srEntityList = 
					sr.findByStatusAndMember_DepartmentAndReqDateBetweenOrderByReqDateAsc(status, de, startDate, endDate);
			for (StuffReqEntity stuffReqEntity : srEntityList) {
				StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
				srDTOList.add(srDTO);
			}
			log.error("size=" + srDTOList.size());
			return srDTOList;	
			
		} else if ( departmentId == 0 ) { //날짜.요청.비품
			log.error("case = 6");
			List<StuffReqEntity> srEntityList = 
					sr.findByStatusAndStuffLikeAndReqDateBetweenOrderByReqDateAsc(status, se, startDate, endDate);
			for (StuffReqEntity stuffReqEntity : srEntityList) {
				StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
				srDTOList.add(srDTO);
			}
			log.error("size=" + srDTOList.size());
			return srDTOList;	
			
		} else if ( status == 3 ) { //날짜.부서.비품
			log.error("case = 7");
			List<StuffReqEntity> srEntityList = 
					sr.findByMember_DepartmentAndStuffLikeAndReqDateBetweenOrderByReqDateAsc(de, se, startDate, endDate);
			for (StuffReqEntity stuffReqEntity : srEntityList) {
				StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
				srDTOList.add(srDTO);
			}
			log.error("size=" + srDTOList.size());
			return srDTOList;	
			
		} else { //날짜. 요청. 부서. 비품
			log.error("case = 8");
			List<StuffReqEntity> srEntityList = 
					sr.findByMember_DepartmentAndStuffLikeAndStatusAndReqDateBetweenOrderByReqDateAsc(de, se, status, startDate, endDate);
			for (StuffReqEntity stuffReqEntity : srEntityList) {
				StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
				srDTOList.add(srDTO);
			}
			log.error("size=" + srDTOList.size());
			return srDTOList;	
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
//		String memberEntity = entity.getMember().getId();
		MemberEntity memberEntity = se.getMember();
		log.warn("비품 승인 id : {}", memberEntity.getId());
        
		 notify.send(memberEntity, NotificationEntity.NotificationType.STUFF, "비품요청이 승인되었습니다.");
		
//        if(se.getStatus() == 1) {
//    	    // 찬석
//    	    notify.send(memberEntity, NotificationEntity.NotificationType.STUFF, "비품요청이 승인되었습니다.");
//
//        } // if
	}
	
	public void modifyReqReject(StuffReqDTO dto) throws ModifyException{
		Optional<StuffReqEntity> optS = sr.findById(dto.getId());
		StuffReqEntity se = optS.get();
        se.modifyStatus(dto.getStatus());
        se.modifyReject(dto.getReject());
        sr.save(se);
        
        // 비품 반려 알림
    	// 찬석
//		String memberEntity = entity.getMember().getId();
		MemberEntity memberEntity = se.getMember();
		log.warn("비품반려 id : {}", memberEntity.getId());
        
		 notify.send(memberEntity, NotificationEntity.NotificationType.STUFF, "비품요청이 반려되었습니다.");
		
//        if(se.getStatus() == 2) {
//    	    // 찬석
//    	    notify.send(memberEntity, NotificationEntity.NotificationType.STUFF, "비품요청이 반려되었습니다.");
//
//        } // if
        
	}

	@Override
	public Long findUnprocessedReqCnt() throws FindException {
		Long status = 0L;
		Long unprocessedReqSize = (long) sr.findByStatus(status).size();
		return unprocessedReqSize;
	}

}
