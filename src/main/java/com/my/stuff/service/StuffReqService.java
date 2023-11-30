package com.my.stuff.service;

import java.util.ArrayList;
import java.util.List;

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
	 * @param StuffReqDTO dto
	 * @throws AddException
	 */
	public void createStuffReq(StuffReqDTO dto) throws AddException{
		StuffReqEntity stuffReqEntity = srm.dtoToEntity(dto);
//		System.out.println("service: "+stuffReqEntity.getStuff().getId());
		sr.save(stuffReqEntity);
	}
	
	/**
	 * DB에서 해당 멤버의 비품요청목록을 가져와 DTO타입으로 변환해 반환한다.
	 * @return List<StuffReqDTO> 
	 * @throws FindException
	 */
	public List<StuffReqDTO> findByMemberId(String memberId) throws FindException{
		MemberEntity me = MemberEntity
				.builder()
				.id(memberId)
				.build();
		
//		List<StuffReqEntity> srEntityList = sr.findByMember(memberId);
		List<StuffReqEntity> srEntityList = sr.findByMember(me);
		List<StuffReqDTO> srDTOList = new ArrayList<>();
		for (StuffReqEntity stuffReqEntity : srEntityList) {
			StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
			srDTOList.add(srDTO);
		}
		
		return srDTOList;
	}
	
	public List<StuffReqDTO> findByMemberIdLikeStuffId(String memberId, String stuffId) throws FindException{
		/*
		 *     	String memberId = "1";
    	MemberEntity me = new MemberEntity();
		me.setId(memberId);
    	
		String stuffId = "S%";  
		StuffEntity se = new StuffEntity();
		se.setId(stuffId);
    	
		List<StuffReqEntity> srList = sr.findByMemberAndStuffLike(me, se);
		log.error("srList = {}", srList.size());
		 */
		
		MemberEntity me = new MemberEntity();
		me.setId(memberId);
    	
//		stuffId += "%";
		String stuffIdM = new String();
		stuffIdM = "%" + stuffId + "%";
		log.error("stuffIdM={}", stuffIdM);
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
	
}
