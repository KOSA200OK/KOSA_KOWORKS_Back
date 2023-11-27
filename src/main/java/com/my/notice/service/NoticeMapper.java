package com.my.notice.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

import com.my.notice.dto.NoticeDTO;
import com.my.notice.entity.NoticeEntity;
import com.my.notice.repository.NoticeRepository;

import lombok.extern.slf4j.Slf4j;


@Configuration
@Slf4j
public class NoticeMapper {
	@Autowired
	NoticeRepository nr;
	
	//DTO->VO 변환 (Notice)
	public NoticeEntity DtoToVo_ModelMapper(NoticeDTO dto) {
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STANDARD)
				.setFieldAccessLevel(AccessLevel.PRIVATE)
				.setFieldMatchingEnabled(true);
		
		Object source = dto;
		Class<NoticeEntity> destinationType = NoticeEntity.class;
		NoticeEntity entity = mapper.map(source, destinationType);
//		log.error("entity memberId:{}, title:{}, content:{}, regdate:{}"
//				,
//				entity.getId(),
//				entity.getMember(),
//				entity.getContent(),
//				entity.getRegdate()
//				);
		return entity;
	}
	
	
	//VO->DTO 변환
	public NoticeDTO VoToDto_ModelMapper(NoticeEntity entity) {
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STANDARD)
				.setFieldAccessLevel(AccessLevel.PRIVATE)
				.setFieldMatchingEnabled(true);
		
		Object source = entity;
		Class<NoticeDTO> destinationType = NoticeDTO.class;
		NoticeDTO dto = mapper.map(source, destinationType);
		
		return dto;
	}
}
