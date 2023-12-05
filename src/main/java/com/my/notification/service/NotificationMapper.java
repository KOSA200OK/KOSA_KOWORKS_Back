package com.my.notification.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import com.my.notification.dao.NotificationRepository;
import com.my.notification.dto.NotificationDTO;
import com.my.notification.dto.NotificationDTO.Response;
import com.my.notification.entity.NotificationEntity;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationMapper {
	
	private NotificationRepository repository;
	
	private ModelMapper mapper;
	
	public NotificationMapper() {
		this.mapper = new ModelMapper();
		this.mapper.getConfiguration()
				   .setMatchingStrategy(MatchingStrategies.STANDARD)
				   .setFieldAccessLevel(AccessLevel.PRIVATE)
				   .setFieldMatchingEnabled(true);
	}
	
	public Response VoToDTO(Response notificationEntity) {

		log.warn("3. VoToDTO의 att =====> {}", notificationEntity);
		
		Object source = notificationEntity;

		Response dto = mapper.map(source, NotificationDTO.Response.class);
		
		return dto;

	} // VoToDTO

}
