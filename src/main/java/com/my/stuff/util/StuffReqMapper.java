package com.my.stuff.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;

import com.my.department.dto.DepartmentDTO;
import com.my.department.entity.DepartmentEntity;
import com.my.member.dto.MemberDTO;
import com.my.member.entity.MemberEntity;
import com.my.stuff.dto.StuffDTO;
import com.my.stuff.dto.StuffReqDTO;
import com.my.stuff.entity.StuffEntity;
import com.my.stuff.entity.StuffReqEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StuffReqMapper {
	
	public static StuffReqEntity dtoToEntity(StuffReqDTO dto) {

		StuffEntity se =  StuffMapper.dtoToEntity(dto.getStuff());
		
		MemberDTO md = dto.getMember();
		MemberEntity me =  MemberEntity.builder()
				  .id(md.getId())
				  .name(md.getName())
				  .password(md.getPassword())
				  .position(md.getPosition())
				  .tel(md.getTel())
//				  .department(de)
				  .build();
		DepartmentDTO dd = md.getDepartment();
		
		if(dd != null && dd.getId() != null) {
			DepartmentEntity de = DepartmentEntity
					.builder()
					.id(dd.getId())
					.name(dd.getName())
					.build();
			me.setDepartment(de);
		}
		
		StuffReqEntity entity = StuffReqEntity.builder()
		                                      .id(dto.getId())
		                                      .stuff(se)
		                                      .member(me)
		                                      .quantity(dto.getQuantity())
		                                      .purpose(dto.getPurpose())
		                                      .build();
		return entity;
	}
	
	
	static public StuffReqDTO entityToDto(StuffReqEntity entity) {
//		ModelMapper mapper = new ModelMapper();
//		mapper.getConfiguration()
//		    .setMatchingStrategy(MatchingStrategies.STANDARD)
//			.setFieldAccessLevel(AccessLevel.PRIVATE)
//			.setFieldMatchingEnabled(true);
		
//		Object source = entity;
//		Class<StuffReqDTO> destinationType = StuffReqDTO.class;
//		StuffReqDTO dto = mapper.map(source, destinationType);
		
        StuffEntity se = entity.getStuff();
        
        
        log.error("entityToDto - 1");
        StuffDTO sd = StuffDTO
        		.builder()
        		.id(se.getId())
        		.name(se.getName())
        		.stock(se.getStock())
        		.build();
        
        MemberEntity me = entity.getMember();
        
        log.error("entityToDto - 2");
        DepartmentEntity de = me.getDepartment();
       
        DepartmentDTO dd = DepartmentDTO
				.builder()
				.id(de.getId()) //또 여기야
				.name(de.getName())
				.build();
        
        log.error("entityToDto - 3");        
		
        MemberDTO md = MemberDTO
				.builder()
				.id(me.getId())
				.department(dd)
				.name(me.getName())
				.password(me.getPassword())
				.position(me.getPosition())
				.tel(me.getTel())
				.build();
        log.error("entityToDto - 4");
		StuffReqDTO dto = StuffReqDTO.builder()
				.id(entity.getId())
				.stuff(sd)
				.member(md)
				.reqDate(entity.getReqDate())
				.quantity(entity.getQuantity())
				.status(entity.getStatus())
				.purpose(entity.getPurpose())
				.reject(entity.getReject())
                .build();
		
		return dto;
	}
}
