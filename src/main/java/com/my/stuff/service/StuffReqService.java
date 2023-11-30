package com.my.stuff.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	private StuffReqMapper srm;

	/**
	 * StuffReqDTO타입의 비품 요청 데이터를 DB에 추가한다
	 * 
	 * @param StuffReqDTO dto
	 * @throws AddException
	 */
	public void createStuffReq(StuffReqDTO dto) throws AddException {
		StuffReqEntity stuffReqEntity = srm.dtoToEntity(dto);
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

//		List<StuffReqEntity> srEntityList = sr.findByMember(memberId);
		List<StuffReqEntity> srEntityList = sr.findByMember(me);
		List<StuffReqDTO> srDTOList = new ArrayList<>();
		for (StuffReqEntity stuffReqEntity : srEntityList) {
			StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
			srDTOList.add(srDTO);
		}

		return srDTOList;
	}

	/**
	 * DB에서 해당 멤버의 비품요청목록중 StuffId에 인자로 넘겨받은 문자열이 포함된 행을 가져와 DTO타입으로 변환해 반환한다.
	 * 
	 * @param memberId
	 * @param stuffId
	 * @return List<StuffReqDTO>
	 * @throws FindException
	 */
	public List<StuffReqDTO> findByMemberIdLikeStuffId(String memberId, String stuffId) throws FindException {

		MemberEntity me = new MemberEntity();
		me.setId(memberId);

//		stuffId += "%";
		String stuffIdM = new String();
		stuffIdM = "%" + stuffId + "%";
//		log.error("stuffNameM={}", stuffIdM);
		StuffEntity se = new StuffEntity();
		se.setId(stuffIdM);

		List<StuffReqEntity> srEntityList = sr.findByMemberAndStuffLike(me, se);
//		List<StuffReqEntity> srEntityList = sr.findByMemberAndStuffContaining(me, se);
		List<StuffReqDTO> srDTOList = new ArrayList<>();
		for (StuffReqEntity stuffReqEntity : srEntityList) {
			StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
			srDTOList.add(srDTO);
		}
		log.error("findByMemberIdLikeStuffId size=" + srDTOList.size());
		return srDTOList;
	}

	/**
	 * stuff_req 테이블에서 사용자가 작성한 비품 요청 행 중 stuffId에 압력한 문자열이포함되는 경우, status값이 일치라는
	 * 경우를 찾아 DTO타입으로 변환해 반환한다
	 * 
	 * @param memberId
	 * @param stuffId
	 * @return List<StuffReqDTO>
	 * @throws FindException
	 */
	public List<StuffReqDTO> findByMemberIdStatusLikeStuffId(String memberId, Long status, String stuffId)
			throws FindException {

		MemberEntity me = new MemberEntity();
		me.setId(memberId);

//		stuffId += "%";
		String stuffIdM = new String();
		stuffIdM = "%" + stuffId + "%";
		log.error("stuffNameM={}", stuffIdM);
		StuffEntity se = new StuffEntity();
		se.setId(stuffIdM);
		List<StuffReqDTO> srDTOList = new ArrayList<>();
		if (status == 3) {
			Long startS = 0L;
			Long endS = 2L;
			List<StuffReqEntity> srEntityList = sr.findByMemberAndStatusBetweenAndStuffLike(me, startS, endS, se);
			for (StuffReqEntity stuffReqEntity : srEntityList) {
				StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
				srDTOList.add(srDTO);
			}
		}else {
			List<StuffReqEntity> srEntityList = sr.findByMemberAndStatusAndStuffLike(me, status, se);			
			for (StuffReqEntity stuffReqEntity : srEntityList) {
				StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
				srDTOList.add(srDTO);
			}
		}
		log.error("findByMemberIdLikeStuffId size=" + srDTOList.size());
		return srDTOList;
	}

	/**
	 * stuff_req 테이블에서 사용자가 작성한 비품 요청 행 중 stuffId에 압력한 문자열이포함되는 경우, status값이 일치라는
	 * 경우를 찾아 DTO타입으로 변환해 반환한다
	 * 
	 * @param memberId
	 * @param stuffId
	 * @return List<StuffReqDTO>
	 * @throws FindException
	 */
//	public List<StuffReqDTO> findByMemberIdStatusLikeStuffIdBetweenDate(String memberId, Long status, String stuffId, Date startDate, Date endDate)
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
//
//		List<StuffReqEntity> srEntityList = sr.findByMemberAndStatusAndStuffLikeAndDateBetween(me, status, se, startDate, endDate);
////		List<StuffReqEntity> srEntityList = sr.findByMemberAndStuffContaining(me, se);
//		List<StuffReqDTO> srDTOList = new ArrayList<>();
//		for (StuffReqEntity stuffReqEntity : srEntityList) {
//			StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
//			srDTOList.add(srDTO);
//		}
//		log.error("findByMemberIdLikeStuffId size=" + srDTOList.size());
//		return srDTOList;
//	}

}
