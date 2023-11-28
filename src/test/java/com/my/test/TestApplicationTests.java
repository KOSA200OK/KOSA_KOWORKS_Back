package com.my.test;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.my.exception.AddException;
import com.my.stuff.dto.StuffDTO;
import com.my.stuff.entity.StuffEntity;
import com.my.stuff.repository.StuffRepository;
import com.my.stuff.repository.StuffReqRepository;
import com.my.stuff.service.StuffMapper;

import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class TestApplicationTests {
	@Autowired
	StuffRepository s;
	StuffReqRepository sr;

	@Test
	@Transactional
	@Commit
	void testInsertStuff() throws AddException {
		StuffDTO dto = StuffDTO.builder().Id("S0005").name("보드마카").quantity(10L).build();
		StuffEntity entity = StuffMapper.dtoToEntity(dto);
		log.error("entity Id:{}, name:{}, quantity:{}", entity.getId(), entity.getName(),
				entity.getQuantity());
		s.save(entity);
		
		Optional<StuffEntity>optEntity1= s.findById(dto.getId());
	
		
		assertTrue(optEntity1.isPresent());
		
		assertEquals("보드마카", optEntity1.get().getName());
		
	}

}
