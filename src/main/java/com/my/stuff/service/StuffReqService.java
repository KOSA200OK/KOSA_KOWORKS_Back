package com.my.stuff.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.internal.objenesis.instantiator.basic.DelegatingToExoticInstantiator;
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
public class StuffReqService {
	@Autowired
	private StuffReqRepository sr;
	
	// 찬석
	@Autowired
	private NotificationServiceImpl notify;
	

	/**
	 * StuffReqDTO타입의 비품 요청 데이터를 DB에 추가한다
	 * 
	 * @param StuffReqDTO dto
	 * @throws AddException
	 */
	public void createStuffReq(StuffReqDTO dto) throws AddException {
		StuffReqEntity stuffReqEntity = StuffReqMapper.dtoToEntity(dto);
//		System.out.println("service: "+stuffReqEntity.getStuff().getId());
		sr.save(stuffReqEntity);
	}
    
	/**
	 * DB에서 해당 멤버의 비품요청목록을 가져와 DTO타입으로 변환해 반환한다.
	 * 
	 * @return List<StuffReqDTO>
	 * @throws FindException
	 */
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

    /**
     * 컨트롤 레이어에서 인계받은 멤버변수의 경우에 따라 변수를 가공하여 레파지토리에서 적절한 메서드를 호출한다
     * @param memberId
     * @param status
     * @param stuffId
     * @param startDate
     * @param endDate
     * @return List<StuffReqDTO>
     * @throws FindException
     */
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
				Long startS = 0L;
				Long endS = 2L;
				List<StuffReqEntity> srEntityList = sr.findByMemberAndReqDateBetweenAndStatusBetween(me, startDate, endDate,
						startS, endS);
				for (StuffReqEntity stuffReqEntity : srEntityList) {
					StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
					srDTOList.add(srDTO);
				}
				return srDTOList;
			
			}else { // 날짜, 비품분류만 선택할 경우 - 요청상태는 전부
				log.error("stuffId = {}", stuffId);
				log.error("case = 3");
				Long startS = 0L;
				Long endS = 2L;
				List<StuffReqEntity> srEntityList = sr.findByMemberAndReqDateBetweenAndStatusBetweenAndStuffLike(me, startDate, endDate, startS, endS, se);
				for (StuffReqEntity stuffReqEntity : srEntityList) {
					StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
					srDTOList.add(srDTO);
				}
				return srDTOList;
			}
		}else {
			if(stuffId.equals("default")) { // 날짜, 요청상태만 선택할 경우
				List<StuffReqEntity> srEntityList = sr.findByMemberAndReqDateBetweenAndStatus(me, startDate, endDate,
						status);
				for (StuffReqEntity stuffReqEntity : srEntityList) {
					StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
					srDTOList.add(srDTO);
				}
				return srDTOList;
			}else { //날짜, 요청상태, 비품분류 전부를 선택했을 경우
				log.error("case = 4");
				List<StuffReqEntity> srEntityList = sr.findByMemberAndReqDateBetweenAndStatusAndStuffLike(me, startDate, endDate, status, se);
				for (StuffReqEntity stuffReqEntity : srEntityList) {
					StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
					srDTOList.add(srDTO);
				}
				
				return srDTOList;
			}			
		}	
	}
	
	/**
	 * 인계된 id에 해당하는 행을 삭제한다
	 * 
	 * @param id
	 */
	public void removeById(Long id) {
		sr.deleteById(id);
	}
	
	
	public List<StuffReqDTO> findByApprove(){
		// 전달할 리스트 세팅
		List<StuffReqDTO> srDTOList = new ArrayList<>();
		//승인 상태인 status값 세팅
		Long approvedStatus = Long.valueOf(0L);
		List<StuffReqEntity> srEntityList = sr.findByStatusOrderByReqDateDesc(approvedStatus);
		for (StuffReqEntity stuffReqEntity : srEntityList) {
			StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
			srDTOList.add(srDTO);
		}
		return srDTOList;
		
	}
	
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
			List<StuffReqEntity> srEntityList = sr.findByReqDateBetween(startDate, endDate);
			for (StuffReqEntity stuffReqEntity : srEntityList) {
				StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
				srDTOList.add(srDTO);
			}
			log.error("size=" + srDTOList.size());
			return srDTOList;
			
		} else if ( (stuffId.equals("default")) && (departmentId == 0) ) { // 날짜. 요청상태 선택
			log.error("case = 2");
			List<StuffReqEntity> srEntityList = sr.findByStatusAndReqDateBetween(status, startDate, endDate);
			for (StuffReqEntity stuffReqEntity : srEntityList) {
				StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
				srDTOList.add(srDTO);
			}
			log.error("size=" + srDTOList.size());
			return srDTOList;
			
		} else if ( (status ==3) && (stuffId.equals("default")) ){ //날짜. 부서 선택
			log.error("case = 3");
			List<StuffReqEntity> srEntityList = sr.findByMember_DepartmentAndReqDateBetween(de, startDate, endDate);
			for (StuffReqEntity stuffReqEntity : srEntityList) {
				StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
				srDTOList.add(srDTO);
			}
			log.error("size=" + srDTOList.size());
			return srDTOList;
			
		} else if ( (status ==3) && (departmentId == 0) ) { //날짜. 비품 선택
			log.error("case = 4");
			List<StuffReqEntity> srEntityList = sr.findByStuffLikeAndReqDateBetween(se, startDate, endDate);
			for (StuffReqEntity stuffReqEntity : srEntityList) {
				StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
				srDTOList.add(srDTO);
			}
			log.error("size=" + srDTOList.size());
			return srDTOList;	
			
		} else if ( stuffId.equals("default") ) { //날짜. 요청. 부서
			log.error("case = 5");
			List<StuffReqEntity> srEntityList = 
					sr.findByStatusAndMember_DepartmentAndReqDateBetween(status, de, startDate, endDate);
			for (StuffReqEntity stuffReqEntity : srEntityList) {
				StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
				srDTOList.add(srDTO);
			}
			log.error("size=" + srDTOList.size());
			return srDTOList;	
			
		} else if ( departmentId == 0 ) { //날짜.요청.비품
			log.error("case = 6");
			List<StuffReqEntity> srEntityList = 
					sr.findByStatusAndStuffLikeAndReqDateBetween(status, se, startDate, endDate);
			for (StuffReqEntity stuffReqEntity : srEntityList) {
				StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
				srDTOList.add(srDTO);
			}
			log.error("size=" + srDTOList.size());
			return srDTOList;	
			
		} else if ( status == 3 ) { //날짜.부서.비품
			log.error("case = 7");
			List<StuffReqEntity> srEntityList = 
					sr.findByMember_DepartmentAndStuffLikeAndReqDateBetween(de, se, startDate, endDate);
			for (StuffReqEntity stuffReqEntity : srEntityList) {
				StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
				srDTOList.add(srDTO);
			}
			log.error("size=" + srDTOList.size());
			return srDTOList;	
			
		} else { //날짜. 요청. 부서. 비품
			log.error("case = 8");
			List<StuffReqEntity> srEntityList = 
					sr.findByMember_DepartmentAndStuffLikeAndStatusAndReqDateBetween(de, se, status, startDate, endDate);
			for (StuffReqEntity stuffReqEntity : srEntityList) {
				StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
				srDTOList.add(srDTO);
			}
			log.error("size=" + srDTOList.size());
			return srDTOList;	
		}
	}
	
	/**
	 * 비품요청 상세내역을 반환한다
	 * @param id
	 * @return
	 */
	public StuffReqDTO findById(Long id) {
		Optional<StuffReqEntity> optS = sr.findById(id);
		StuffReqEntity se = optS.get();
		StuffReqDTO sd = StuffReqMapper.entityToDto(se);
		return sd;
	}
	
	/**
	 * 비품요청 행의 요청상태와 반려사유를 수정한다.
	 * @param id
	 * @param dto
	 * @throws ModifyException
	 */
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
        
        if(se.getStatus() == 1) {
    	    // 찬석
    	    notify.send(memberEntity, NotificationEntity.NotificationType.STUFF, "비품요청이 승인되었습니다.");

        } // if
	}
	
	/**
	 * 비품요청 행의 요청상태와 반려사유를 수정한다.
	 * @param id
	 * @param dto
	 * @throws ModifyException
	 */
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
        
        if(se.getStatus() == 2) {
    	    // 찬석
    	    notify.send(memberEntity, NotificationEntity.NotificationType.STUFF, "비품요청이 반려되었습니다.");

        } // if
        
	}
}
