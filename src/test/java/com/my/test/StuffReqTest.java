package com.my.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.my.department.entity.DepartmentEntity;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.member.dto.MemberDTO;
import com.my.member.entity.MemberEntity;
import com.my.stuff.dto.StuffDTO;
import com.my.stuff.dto.StuffReqDTO;
import com.my.stuff.entity.StuffEntity;
import com.my.stuff.entity.StuffReqEntity;
import com.my.stuff.repository.StuffRepository;
import com.my.stuff.repository.StuffReqRepository;
import com.my.stuff.util.StuffMapper;
import com.my.stuff.util.StuffReqMapper;

import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class StuffReqTest {
	@Autowired
	StuffRepository s;
	
	@Autowired
	StuffReqRepository sr;
	
//	@Test
//	@Transactional
//	@Commit
//	void testFindByStuffReqEntity_DepartEnt_Name() {
////    	String memberId = "0008";
////    	MemberEntity me = new MemberEntity();
////		me.setId(memberId);
////		String nameD = new String("개발2팀");
//		
//		Long id = Long.valueOf(2L);
//		DepartmentEntity de = DepartmentEntity.builder()
//                .id(id)
//                .build();
//		
//		List<StuffReqEntity> srList = sr.findByMember_DepartmentAndReqDateBetween(de);
//		log.error("srList = {}", srList.size());
//		int lisrSize = srList.size();
//		assertTrue(lisrSize==8);
//	}
	
//    @Test
//    void testFindByMemberIdLike() {
//    	String memberId = "1";
//    	MemberEntity me = new MemberEntity();
//		me.setId(memberId);
//    	
//		String stuffId = "S%";  
//		StuffEntity se = new StuffEntity();
//		se.setId(stuffId);
//    	
//		List<StuffReqEntity> srList = sr.findByMemberAndStuffLike(me, se);
//		log.error("srList = {}", srList.size());
    
//		List<StuffReqDTO> srDTOList = new ArrayList<>();
//		for (StuffReqEntity stuffReqEntity : srList) {
//			StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
//			srDTOList.add(srDTO);
//		}
////		log.error("findByMember(1L) = {}", srDTOList);
//    }
//	
//	
//	
//	@Test
//	void testFindByMemberId() {
//		MemberEntity me = new MemberEntity();
//		me.setId("1");
////		String memberId = "1";
////		List<StuffReqEntity> srList = sr.findByMember(memberId);
//		List<StuffReqEntity> srList = sr.findByMember(me);
//		List<StuffReqDTO> srDTOList = new ArrayList<>();
//		for (StuffReqEntity stuffReqEntity : srList) {
//			StuffReqDTO srDTO = StuffReqMapper.entityToDto(stuffReqEntity);
//			srDTOList.add(srDTO);
//		}
//		log.error("findByMember(1L) = {}", srDTOList);
//		int lisrSize = srDTOList.size();
//		assertTrue(lisrSize==12);
//	}
	
	@Test
//	@Transactional
//	@Commit
	void testSaveStuffReq() {
		StuffDTO sdto = StuffDTO
				.builder()
				.id("S0005")
//				.name("보드마카")
//				.quantity(10L)
				.build();
		
		MemberDTO mDto = MemberDTO
				.builder()
				.id("1")
//				.password("1")
//				.name("서재원")
//				.position("사원")
//				.tel("0")
//				.password("s")
//				.departmentId(10L)
				.build();
		StuffReqDTO srdto = StuffReqDTO
				.builder()
//				.id(100L)
			    .stuff(sdto)
			    .member(mDto)
			    .quantity(3L)
				.purpose("회의 준비")
				.build();
		
		StuffReqEntity entity = StuffReqMapper.dtoToEntity(srdto);
		
		StuffReqEntity savedEntity =    sr.save(entity);
		log.error("savedEntity Id:{}, stuffId:{}, memberId:{}, quantityReq:{}, purpose:{}", 
				savedEntity.getId(), savedEntity.getStuff().getId(), savedEntity.getMember().getId(), savedEntity.getQuantity(), savedEntity.getPurpose());
		Optional<StuffReqEntity> optEntityReq = sr.findById(savedEntity.getId());

		assertTrue(optEntityReq.isPresent());

		assertEquals("보드마카", optEntityReq.get().getStuff().getName());
	}

	@Test
	@Transactional
	@Commit
	void testInsertStuff() throws AddException {
		StuffDTO dto = StuffDTO.builder().id("S0005").name("보드마카").stock(10L).build();
		StuffEntity entity = StuffMapper.dtoToEntity(dto);
		log.error("entity Id:{}, name:{}, quantity:{}", entity.getId(), entity.getName(), entity.getStock());
		s.save(entity);

		Optional<StuffEntity> optEntity1 = s.findById(dto.getId());

		assertTrue(optEntity1.isPresent());

		assertEquals("보드마카", optEntity1.get().getName());

	}

	@Test
	@Transactional
	void testFindAll() throws FindException {
		List<StuffEntity> list = s.findAll();
		log.error("findAll() = {}", list);
	}

}