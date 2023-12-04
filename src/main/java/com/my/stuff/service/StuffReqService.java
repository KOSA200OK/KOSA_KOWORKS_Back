package com.my.stuff.service;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.department.entity.DepartmentEntity;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.member.entity.MemberEntity;
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
				log.error("case = 1");
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
				log.error("case = 2");
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
	
//	public List<StuffReqDTO> findByManageCase(String memberId, Long departmentId, Long status, String stuffId, Date startDate, Date endDate) throws FindException {
//        
//		switch (key) {
//		case value:
//			
//			break;
//
//		default:
//			break;
//		}
//	    return null;
//	}
	
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
//	
//	public List<StuffReqDTO> findByDepartment(Long departmentId){
//		// 전달할 리스트 세팅
//		List<StuffReqDTO> srDTOList = new ArrayList<>();
//		//승인 상태인 status값 세팅
//		DepartmentEntity de = DepartmentEntity.builder()
//				                              .id(departmentId)
//				                              .build();
//		List<StuffReqEntity> srEntityList = sr.findByMember_Department(de);
//		for (StuffReqEntity stuffReqEntity : srEntityList) {
//			StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
//			srDTOList.add(srDTO);
//		}
//		return srDTOList;
//		
//	}
	
	

//	public List<StuffReqDTO> findByDate(String memberId, Date startDate, Date endDate) throws FindException {
//		MemberEntity me = MemberEntity.builder().id(memberId).build();
//
//		List<StuffReqEntity> srEntityList = sr.findByMemberAndReqDateBetween(me, startDate, endDate);
//		List<StuffReqDTO> srDTOList = new ArrayList<>();
//		for (StuffReqEntity stuffReqEntity : srEntityList) {
//			StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
//			srDTOList.add(srDTO);
//		}
//
//		return srDTOList;
//	}
	
//	/**
//	 * DB에서 해당 멤버의 비품요청목록중 StuffId에 인자로 넘겨받은 문자열이 포함된 행을 가져와 DTO타입으로 변환해 반환한다.
//	 * 
//	 * @param memberId
//	 * @param stuffId
//	 * @return List<StuffReqDTO>
//	 * @throws FindException
//	 */
//	public List<StuffReqDTO> findByMemberIdLikeStuffId(String memberId, String stuffId) throws FindException {
//
//		MemberEntity me = new MemberEntity();
//		me.setId(memberId);
//
////		stuffId += "%";
//		String stuffIdM = new String();
//		stuffIdM = "%" + stuffId + "%";
////		log.error("stuffNameM={}", stuffIdM);
//		StuffEntity se = new StuffEntity();
//		se.setId(stuffIdM);
//
//		List<StuffReqEntity> srEntityList = sr.findByMemberAndStuffLike(me, se);
////		List<StuffReqEntity> srEntityList = sr.findByMemberAndStuffContaining(me, se);
//		List<StuffReqDTO> srDTOList = new ArrayList<>();
//		for (StuffReqEntity stuffReqEntity : srEntityList) {
//			StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
//			srDTOList.add(srDTO);
//		}
//		log.error("findByMemberIdLikeStuffId size=" + srDTOList.size());
//		return srDTOList;
//	}
	
//	/**
//	 * stuff_req 테이블에서 사용자가 작성한 비품 요청 행 중 stuffId에 압력한 문자열이포함되는 경우, status값이 일치라는
//	 * 경우를 찾아 DTO타입으로 변환해 반환한다
//	 * 
//	 * @param memberId
//	 * @param stuffId
//	 * @return List<StuffReqDTO>
//	 * @throws FindException
//	 */
//	public List<StuffReqDTO> findByMemberIdStatusLikeStuffId(String memberId, Long status, String stuffId)
//			throws FindException {
//
//		MemberEntity me = new MemberEntity();
//		me.setId(memberId);
//
////		stuffId += "%";
//		String stuffIdM = new String();
//		stuffIdM = "%" + stuffId + "%";
//		log.error("stuffNameM={}", stuffIdM);
//		StuffEntity se = new StuffEntity();
//		se.setId(stuffIdM);
//		List<StuffReqDTO> srDTOList = new ArrayList<>();
//		if (status == 3) {
//			Long startS = 0L;
//			Long endS = 2L;
//			List<StuffReqEntity> srEntityList = sr.findByMemberAndStatusBetweenAndStuffLike(me, startS, endS, se);
//			for (StuffReqEntity stuffReqEntity : srEntityList) {
//				StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
//				srDTOList.add(srDTO);
//			}
//		} else {
//			List<StuffReqEntity> srEntityList = sr.findByMemberAndStatusAndStuffLike(me, status, se);
//			for (StuffReqEntity stuffReqEntity : srEntityList) {
//				StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
//				srDTOList.add(srDTO);
//			}
//		}
//		log.error("findByMemberIdLikeStuffId size=" + srDTOList.size());
//		return srDTOList;
//	}


}
