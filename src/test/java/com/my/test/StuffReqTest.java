package com.my.test;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.stuff.dto.StuffDTO;
import com.my.stuff.dto.StuffReqDTO;
import com.my.stuff.entity.StuffEntity;
import com.my.stuff.entity.StuffReqEntity;
import com.my.stuff.repository.StuffRepository;
import com.my.stuff.repository.StuffReqRepository;
import com.my.stuff.service.StuffMapper;
import com.my.stuff.service.StuffReqMapper;

import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class StuffReqTest {
	@Autowired
	StuffRepository s;
	StuffReqRepository sr;

	@Test
	@Transactional
	@Commit
	void testSaveStuffReq() throws AddException {
		StuffReqDTO dto = StuffReqDTO
				.builder()
				.stuffId("S0005")
				.memberId(1L)
				.quantityReq(3L)
				.purpose("회의 준비")
				.build();
		StuffReqEntity entity = StuffReqMapper.dtoToEntity(dto);
		sr.save(entity);
		
		Optional<StuffReqEntity> optEntityReq = sr.findById(dto.getId());

		assertTrue(optEntityReq.isPresent());

		assertEquals("S0005", optEntityReq.get().getStuffEntity().getName());
	}

	@Test
	@Transactional
	@Commit
	void testInsertStuff() throws AddException {
		StuffDTO dto = StuffDTO.builder().Id("S0005").name("보드마카").quantity(10L).build();
		StuffEntity entity = StuffMapper.dtoToEntity(dto);
		log.error("entity Id:{}, name:{}, quantity:{}", entity.getId(), entity.getName(), entity.getQuantity());
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